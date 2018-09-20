# springboot-oauth2-spring-security-mysql
This project will show you oauth2 integration as well spring security (Spring role management.)

### Get access token request.
```
URL: http://localhost:8081/oauth/token
Method: POST
Authorization: Basic Auth/username(ClientId)/password(secret)
Content-Type: application/x-www-form-urlencoded
Body:
  username: <username>
  password: <password>
  grant_type: password
  
 Response:
  {
    "access_token": "43c76f19-724e-40d8-8929-2a01a458e5a3",
    "token_type": "bearer",
    "refresh_token": "ab3df451-b068-44fb-942f-af62cbe290a2",
    "expires_in": 899,
    "scope": "read"
}
```


### Get access token using refresh_token request.
```
URL: http://localhost:8081/oauth/token
Method: POST
Authorization: Basic Auth/username(ClientId)/password(secret)
Content-Type: application/x-www-form-urlencoded
Body:
  grant_type: refresh_token
  refresh_token: <Enter refresh token>
  
 Response:
{
    "access_token": "fdd123a0-9e65-48e7-8a90-59d5e1d081cc",
    "token_type": "bearer",
    "refresh_token": "af3dd145-ae30-403a-afcb-ae20f944789d",
    "expires_in": 899,
    "scope": "read"
}
```


### Register new user.
```
URL: http://localhost:8081/user/registration?access_token=92e469fc-84c4-4844-9e10-7e045fac231a
Method: POST
Content-Type: application/json
Body:
  {
	"username":"deep",
	"password":"12345",
	"roles": [
        {
            "name": "ROLE_USER"
        }
    ]
}
  
 Response:
{
    "status": "200",
    "listCount": null,
    "message": "User has been registered successfully",
    "errorText": null,
    "result": {
        "id": 13,
        "username": "deepakk",
        "password": "12345",
        "roles": [
            {
                "id": 1,
                "name": "ROLE_USER"
            }
        ]
    }
}
```

**Note:** *If you want to register User with User oauth token then it will give you access_denied error. User will be only created by those user who have ADMIN role. You will find those API in code (UserController).*

# Thank you!

