import requests
import json

BASE_URL = "http://localhost:5000/api"
ENDPOINT = "/hotelbeds-hotels-booking-hotel-availability"

def test_get_hotel_availability():
    url = BASE_URL + ENDPOINT
    headers = {
        "Content-Type": "application/json"
    }
    request_body = {
        # Add appropriate request body parameters here
        "stay": {
            "checkIn": "2023-12-20",
            "checkOut": "2023-12-25"
        },
        "occupancies": [
            {
                "rooms": 1,
                "adults": 2,
                "children": 0
            }
        ],
        "destination": {
            "code": "PMI"
        }
    }

    response = requests.post(url, headers=headers, data=json.dumps(request_body))
    
    assert response.status_code == 200, f"Expected status code 200, but got {response.status_code}"
    print("Response Status Code:", response.status_code)
    print("Response Body:", response.json())

if __name__ == "__main__":
    test_get_hotel_availability()