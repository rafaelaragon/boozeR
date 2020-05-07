import json
import boto3

client = boto3.client('dynamodb')

def lambda_handler(event, context):
    name = event["queryStringParameters"]["name"]
    type = event["queryStringParameters"]["type"]
    price = event["queryStringParameters"]["price"]
    vol = event["queryStringParameters"]["vol"]
    det = event["queryStringParameters"]["det"]
    url = event["queryStringParameters"]["url"]
    
    response = client.put_item(Item={
        'name': { "S": name },
        'type': { "S": type },
        'price': { "N": price },
        'graduation': { "N": vol },
        'details': { "S": det },
        'image': { "S": url} },
        TableName='Drinks')
    return {
        'statusCode': 200,
        'headers': {
            "Access-Control-Allow-Origin": "*"
        },
        'body': json.dumps(response)
    }
