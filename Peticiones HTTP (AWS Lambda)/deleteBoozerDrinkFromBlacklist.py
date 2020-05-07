import json
import boto3

dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
table = dynamodb.Table('Users')
client = boto3.client('dynamodb')

def lambda_handler(event, context):
    uid = event["queryStringParameters"]["uid"]
    drink = event["queryStringParameters"]["drink"]
    fixedList = []
    position = -1
    
    #As it's not possible to use DELETE on a List in DynamoDB(yet), I need to
    #get every blacklisted drink of the specified user to get the drink's position
    userInfo = client.get_item(Key={'Uid': { "S": uid } }, AttributesToGet=["Blacklisted"], TableName='Users')
    blackListDrinks = userInfo.get("Item").get("Blacklisted").get("L")
    for x in blackListDrinks:
        fixedList.append(x['S'])
    print(fixedList)
    if drink in fixedList:
        position = fixedList.index(drink)
        
    #If the drink exists on the list, REMOVE it using it's position
    if position != -1:
        response = table.update_item(
            Key={'Uid': uid},
            UpdateExpression="REMOVE Blacklisted["+ str(position) +"]",
            )
    print(position)
    
    return {
        'statusCode': 200,
        'headers': {
            "Access-Control-Allow-Origin": "*"
        },
        'body': json.dumps(response)
    }
