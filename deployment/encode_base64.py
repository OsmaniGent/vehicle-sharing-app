import base64

# Replace 'vehicle-sharing-web-fda3571fcf2a.json' with your actual file name
with open("vehicle-sharing-web-fda3571fcf2a.json", "rb") as json_file:
    encoded_string = base64.b64encode(json_file.read()).decode('utf-8')

# Write the base64 string to a file
with open("base64.txt", "w") as base64_file:
    base64_file.write(encoded_string)

print("Base64 encoding complete. Check the base64.txt file.")
