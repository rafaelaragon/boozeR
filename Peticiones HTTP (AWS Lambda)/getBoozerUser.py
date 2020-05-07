import json
import boto3

client = boto3.client('dynamodb')

def lambda_handler(event, context):
    
    uid = event["queryStringParameters"]["uid"]
    print(client)
    response = client.get_item(Key={'Uid': { "S": uid } }, TableName='Users')
    return {
        'statusCode': 200,
        'body': json.dumps(response)
    }
