```toml
name = 'PUT Product'
method = 'PUT'
url = 'http://localhost:8181/api/v1/products/1'
sortWeight = 4000000
id = '0daf32d6-c90c-4675-b3bb-d708ac9a093f'

[[headers]]
key = 'Content-Type'
value = 'application/json'

[body]
type = 'JSON'
raw = '''
{
  "productName": "iPad 2024",
  "productPrice": 24500,
  "productQuantity": 1,
  "productImage": "ipad.jpg"
}'''
```
