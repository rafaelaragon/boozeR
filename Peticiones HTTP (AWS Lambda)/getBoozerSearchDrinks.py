import json
import boto3

client = boto3.client('dynamodb')

def lambda_handler(event, context):
    
    search = event["queryStringParameters"]["search"]
    
    #Get all drinks
    pe = "#n, image"
    ean = {'#n': 'name'}
    content = client.scan(TableName='Drinks', ProjectionExpression = pe, ExpressionAttributeNames = ean)
    response = content['Items']
    
    i = 0
    filterResponse = []
    for d in response:
        #if the drinkname contains the user's search, add it to the final response
        if str.lower(search) in str.lower(d['name']['S']):
            filterResponse.append(d)
        i+=1
    
    return {
        'statusCode': 200,
        'body': json.dumps(filterResponse)
    }
