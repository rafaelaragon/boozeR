import json
import boto3

client = boto3.client('dynamodb')

def lambda_handler(event, context):
    
    name = event["queryStringParameters"]["name"]
    print(client)
    response = client.get_item(Key={'name': { "S": name } }, TableName='Drinks')
    return {
        'statusCode': 200,
        'body': json.dumps(response)
    }
