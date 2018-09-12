<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
  <head>
    <title>Index</title>
  </head>
    <h4>List of products</h4> <a href="/create">Create Product</a>

    <table>
      <th>Name</th>
      <th>Description</th>
      <th>Price</th>
      <th>Stock</th>
      <c:forEach items="${products}" var="p">
        <tr>
          <td>${p.name}</td>
          <td>${p.description}</td>
          <td>${p.price}</td>
          <td>${p.stock}</td>
        </tr>
      </c:forEach>
    </table>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>

</html>
