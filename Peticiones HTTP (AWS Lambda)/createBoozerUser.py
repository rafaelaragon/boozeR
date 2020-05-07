import json
import boto3

client = boto3.client('dynamodb')

def lambda_handler(event, context):
    uid = event["queryStringParameters"]["uid"]
    email = event["queryStringParameters"]["email"]
    user = event["queryStringParameters"]["user"]
    isAdmin = False
    
    response = client.put_item(Item={
        'Uid': { 'S': uid },
        'Email': { 'S': email },
        'User': { 'S': user },
        'isAdmin': { 'BOOL': isAdmin },
        'Favorites': { 'L': [] },
        'Blacklisted': { 'L': [] } },
        TableName='Users')
    return {
        'statusCode': 200,
        'headers': {
            "Access-Control-Allow-Origin": "*"
        },
        'body': json.dumps(response)
    }
