import json
import boto3

client = boto3.client('dynamodb')

def lambda_handler(event, context):
    
    uid = event["queryStringParameters"]["uid"]
    drinkType = event["queryStringParameters"]["type"]
    drinkPrice = float(event["queryStringParameters"]["price"])
    drinkVol = float(event["queryStringParameters"]["vol"])
    showBlacklist = event["queryStringParameters"]["blacklist"]
    
    #get user's blacklisted drinks
    user = client.get_item(Key={'Uid': { "S": uid } }, TableName='Users')
    blacklist = user['Item']['Blacklisted']['L']
    auxList = []
    i = 0
    for b in blacklist:
        auxList.append(b['S'])
    
    #Get all drinks
    pe = "#n, image, graduation, #t, price"
    ean = {'#n': 'name', '#t': 'type'}
    content = client.scan(TableName='Drinks', ProjectionExpression = pe, ExpressionAttributeNames = ean)
    response = content['Items']
    
    i = 0
    filterResponse = []
    for d in response:
        #if no type is selected, or if the type matches
        if drinkType in response[i]["type"]["S"] or drinkType == "Cualquiera":
            #if no max price is selected, or if the price is lower than the given one
            if (drinkPrice >= float(response[i]["price"]["N"]) or drinkPrice >= 50):
                #if the graduation is lower than the given one
                if drinkVol >= float(response[i]["graduation"]["N"]):
                    filterResponse.append(d)
        i+=1
        
    #if blacklisted drinks aren't wanted
    if showBlacklist == "False":
        for d in filterResponse:
            if d['name']['S'] in auxList:
                filterResponse.remove(d)
    
    return {
        'statusCode': 200,
        'body': json.dumps(filterResponse)
    }
