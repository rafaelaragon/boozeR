import json
import boto3

client = boto3.client('dynamodb')

def lambda_handler(event, context):
    print(client)
    uid = event["queryStringParameters"]["uid"]
    response = client.delete_item(Key={'Uid': { "S": uid } }, TableName='Users')
    return {
        'statusCode': 200,
        'body': json.dumps(response)
    }
