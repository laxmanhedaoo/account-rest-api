### Problem Statement
- Build Restful API to transfer money between accounts.
- Keep it simple, no authentication and authorization required.
- Demo with tests on rest API.

### Proposed Solution
- Building RESTful APIs on Reactive programming and Events Driven Model.
- API execution is asynchronous and non-blocking.
- A lightweight REST API which is fast to develop, faster to build, fastest to deploy.


### Start Program
First build program and then execute it. It will deploy REST API on port **8080** and API endpoints will be bounded to the localhost

```sh
$ mvn clean install
$ java -jar target\money-transfer-1.0.0-SNAPSHOT-fat.jar
```

### API Endpoints

#### Account API
<table>
   <thead>
      <tr>
         <th>Endpoint</th>
         <th>Description</th>
         <th>Parameters</th>
         <th>Response</th>
      </tr>
   </thead>
   <tbody>
      <tr>
         <td><code>GET /accounts</code></td>
         <td>Gives all accounts details</td>
         <td>-</td>
         <td>
            <pre>
[
   {
     "accountId": 15671753194731,
     "name": "Laxman",
     "balance": 1000.0,
     "currency": "USD",
     "createdTime": "2019-08-25T13:52Z"
   },
   {
     "accountId": 15671753194732,
     "name": "CustomerName",
     "balance": 1000.0,
     "currency": "USD",
     "createdTime": "2019-08-25T13:52Z"
   }
]
            </pre>
         </td>
      </tr>
      <tr>
         <td><code>GET /accounts/id </code></td>
         <td>Gives Account detail of input id</td>
         <td>Account Id</td>
         <td>
            <pre>
{
  "accountId": 15671753194731,
  "name": "Laxman",
  "balance": 1000.0,
  "currency": "USD",
  "createdTime": "2019-08-25T13:52Z"
}
            </pre>
         </td>
      </tr>
      <tr>
         <td><code>POST /accounts </code></td>
         <td>Create new Account</td>
         <td> Request Body           
            <pre>
{ 
  "name": "newCustomerName",
  "balance": 1000.0,
  "currency": "USD"
}
            </pre></td>
         <td>
            <pre>
{
  "message": "Account successfully created",
  "status": 200,
  "accountId": 6425165732534687307,
  "createdOn": "2019-08-25T13:35Z"
}
            </pre>
         </td>
      </tr>
      <tr>
         <td><code>PUT /accounts/id </code></td>
         <td>Update Account detail of input id</td>
         <td> Account Id and
        Request Body           
            <pre>
{ 
  "name": "ChangedCustomerName",
  "balance": 2000.0,
  "currency": "EUR"
}
            </pre>
        </td>
         <td>
            <pre>
{
  "message": "Account successfully updated",
  "status": 200,
  "accountId": 6425165732534687307,
  "createdOn": "2019-08-25T13:35Z"
}
            </pre>
         </td>
      </tr>
   </tbody>
</table>

#### Transfer API
<table>
   <thead>
      <tr>
         <th>Endpoint</th>
         <th>Description</th>
         <th>Parameters</th>
         <th>Response</th>
      </tr>
   </thead>
   <tbody>
      <tr>
         <td><code>GET /transfers</code></td>
         <td>Gives all transfers details</td>
         <td>-</td>
         <td>
            <pre>
[
  {
    "transferId": "7acc9b0d-3c57-4c3d-8215-945e9c823a60",
    "sourceAccountId": 15671753194731,
    "targetAccountId": 15671753194732,
    "amount": 10.0,
    "currency": "USD",
    "comment": "Test"
  },
  {
    "transferId": "2a2a817e-be7b-49f7-a488-eb4098ccd2e4",
    "sourceAccountId": 15671753194732,
    "targetAccountId": 15671753194733,
    "amount": 10.0,
    "currency": "USD",
    "comment": "Test"
  }
]
            </pre>
         </td>
      </tr>
      <tr>
         <td><code>GET /transfers/id </code></td>
         <td>Gives transfer detail of input id</td>
         <td>Transfer Id</td>
         <td>
            <pre>
{
   "transferId": "7acc9b0d-3c57-4c3d-8215-945e9c823a60",
   "sourceAccountId": 15671753194731,
   "targetAccountId": 15671753194732,
   "amount": 10.0,
   "currency": "USD",
   "comment": "Test"
}
            </pre>
         </td>
      </tr>
      <tr>
         <td><code>POST /transfers </code></td>
         <td>Transfer money between accounts</td>
         <td> Request Body           
            <pre>
{
    "sourceAccountId": 15671753194732,
    "targetAccountId": 15671753194731,
    "amount": "8.00",
    "currency": "USD",
    "comment": "Test"
}
            </pre></td>
         <td>
            <pre>
{
  "message": "transfer request processed",
  "status": 200,
  "transferId": "1778c767-e4a6-4bed-b855-40b64c9b14c1",
  "transferState": "COMPLETED",
  "transferDate": "2019-08-25T18:54Z"
}
            </pre>
         </td>
      </tr>
       
   </tbody>
</table>

### Application Design


### Testing
> Working on Unit and Integration test cases, soon will be available

### Built on
| Library | References |
| -----------------| ---------- |
| RxJava | https://github.com/ReactiveX/RxJava |
| Vert.x | https://vertx.io/ |
| Guice  | https://github.com/google/guice |



