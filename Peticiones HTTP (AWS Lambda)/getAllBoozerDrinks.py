import json
import boto3

client = boto3.client('dynamodb')

def lambda_handler(event, context):
    
    #Get all drinks
    pe = "#n, image"
    ean = {'#n': 'name'}
    content = client.scan(TableName='Drinks', ProjectionExpression = pe, ExpressionAttributeNames = ean)
    response = content['Items']
    
    
    return {
        'statusCode': 200,
        'body': json.dumps(response)
    }
