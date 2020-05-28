import json
import boto3
from boto3.dynamodb.conditions import Key, Attr

client = boto3.client('dynamodb')

def lambda_handler(event, context):
    
    uid = event["queryStringParameters"]["uid"]
    
    #get user's favorite drinks
    user = client.get_item(Key={'Uid': { "S": uid } }, TableName='Users')
    favorites = user['Item']['Favorites']['L']
    auxList = []
    i = 0
    for f in favorites:
        auxList.append(f['S'])
    
    #get all drinks
    pe = "#n, image"
    ean = {'#n': 'name'}
    content = client.scan(TableName='Drinks', ProjectionExpression = pe, ExpressionAttributeNames = ean)
    response = content['Items']
    
    #add drinks that the user likes to response
    favList = []
    for d in response:
        if d['name']['S'] in auxList:
            favList.append(d)
    
    return {
        'statusCode': 200,
        'body': json.dumps(favList)
    }
