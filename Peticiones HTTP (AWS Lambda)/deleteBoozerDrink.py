import json
import boto3

client = boto3.client('dynamodb')

def lambda_handler(event, context):
    print(client)
    name = event["queryStringParameters"]["name"]
    response = client.delete_item(Key={'name': { "S": name } }, TableName='Drinks')
    return {
        'statusCode': 200,
        'body': json.dumps(response)
    }
