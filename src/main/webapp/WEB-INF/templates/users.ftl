<html>
<head>
    <title>Users</title>
</head>
<body>
<ul>
    <#list users as user>
        <li><span>${user.name}</span> <span>${user.surname}</span> <span>${user.email}</span></li>
    </#list>
</ul>
<a href="/users/new">add new user</a>
</body>
</html>