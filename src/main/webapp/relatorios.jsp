<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>

<h2>Relatórios</h2>

<h3>Cadastros realizados por Perfil</h3>

<table border="1">
    <tr>
        <th>Perfil</th>
        <th>Total</th>
    </tr>

    <c:choose>

        <c:when test="${not empty totalPorPerfil}">
            <c:forEach var="item" items="${totalPorPerfil}">
                <tr>
                    <td>${item.key}</td>
                    <td>${item.value}</td>
                </tr>
            </c:forEach>
        </c:when>

        <c:otherwise>
            <tr>
                <td colspan="2">Nenhum dado encontrado</td>
            </tr>
        </c:otherwise>

    </c:choose>

</table>

<br>
<a href="dashboard">Voltar</a>