### Problem Statement
- Build Restful API to transfer money between accounts.
- Keep it simple, no authentication and authorization required
- It should be Lightweight standalone API
- Demo with tests on rest API

### Start Program
First build application and then execute it. It will deploy REST API on port **8080** and API endpoints will be bounded to the localhost

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


### Design


### Testing
> Working on Unit and Integration test cases, soon will be available

### Built on
