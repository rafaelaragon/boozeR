import json
import boto3

dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
table = dynamodb.Table('Users')

def lambda_handler(event, context):
    uid = event["queryStringParameters"]["uid"]
    drink = event["queryStringParameters"]["drink"]
        
    #Use SET to append the drink in the favorite's list
    response = table.update_item(
        Key={'Uid': uid},
        UpdateExpression="SET Favorites = list_append(Favorites, :d)",
        ExpressionAttributeValues={':d': [drink]}
        )
    return {
        'statusCode': 200,
        'headers': {
            "Access-Control-Allow-Origin": "*"
        },
        'body': json.dumps(response)
    }
