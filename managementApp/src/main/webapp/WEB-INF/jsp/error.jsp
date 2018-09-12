<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Errors</title>
    </head>
    <body>
        <h1>Errors</h1>
        <div>
            <ul>
                <c:forEach var="error" items="${errors}">
                    <li>
                        <div>
                        <br/>
                          <strong><c:out value="${error}"/>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </body>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>
</html>