```toml
name = 'POST Product'
method = 'POST'
url = 'http://localhost:8181/api/v1/products'
sortWeight = 3000000
id = '5a17fbbf-de3e-4376-8025-2ff7184cfb86'

[[headers]]
key = 'Content-Type'
value = 'application/json'

[body]
type = 'JSON'
raw = '''
{
  "productName": "iPhone 16 Pro",
  "productPrice": 24500.50,
  "productQuantity": 1,
  "productImage": "iphone.jpg"
}'''
```
