### Opening account API

#### Installation

##### Locally

Clone the repository:
```sh
git clone https://github.com/oussamamessaoudi/opening_account.git
```

Build the project:
```sh
cd opening_account
./gradlew clean build
```

Run the application:
```sh
./gradlew bootRun
```

##### Using Docker Image

Pull the Docker image:
```sh
docker pull oussmess/account-opening:latest
```

Run the Docker container:
```sh
docker run --name account-opening -p 8080:8080 -d oussmess/account-opening
```

#### Endpoints

##### To Beautify Response

- On Linux:
  ```sh
  sudo apt install jq
  ```

- On macOS:
  ```sh
  brew install jq
  ```

##### Get Customer Details
To get details of a customer:
```sh
curl http://localhost:8080/api/customer?id=1 | jq .
```

##### Create New Account
To create a new account:
```sh
curl -X POST -H "Content-Type: application/json" -d '{
  "customerId": 2,
  "initialCredit": -100
}' http://localhost:8080/api/account | jq .
```
