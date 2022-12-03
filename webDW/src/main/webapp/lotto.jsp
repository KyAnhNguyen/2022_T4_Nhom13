<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%--%>
<%--    request.setCharacterEncoding("UTF-8");--%>
<%--    response.setCharacterEncoding("UTF-8");--%>
<%--%>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xổ số </title>
    <link rel="stylesheet" href="lotto.css">
</head>

<body>
<div class="container">
    <div class="export">
        <a download="xoSoMienNam.png" id="png" href="' + img + '">Export PNG</a>

        <button class="btn_print" onclick="pdf()"> Export PDF</button>

        <button type="button" onclick="tableToCSV()">
            Export CSV
        </button>
    </div>

    <div class="box_kqxs" id="xoso">
        <div class="title">
            <div>KẾT QUẢ XỔ SỔ MIỀN NAM</div>
        </div>
        <table id="table">
            <tbody>
            <tr>
                <td class="col">
                    <table class="table-child1">
                        <tbody>
                        <tr>
                            <td>${dateDim.getDay_of_week()}</td>
                        </tr>
                        <tr>
                            <td>${dateDim.getFull_date()}</td>
                        </tr>
                        <c:forEach items="${prizeList}" var="p">
                            <tr>
                                <td>${p.getName_prize()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>

                <td>
                    <table class="table-child1">
                        <tbody>
                        <tr>

                            <c:forEach items="${provinceList}" var="pro">
                                <td class="col">
                                    <table>
                                        <tbody>
                                        <tr>
                                            <td>${pro.getName_province()}</td>
                                        </tr>
                                        <tr>
                                            <td>${pro.getId_province()}</td>
                                        </tr>
                                        <c:forEach items="${prizeList}" var="p">
                                            <c:if test="${lottoService.getAllLottoByDPP(pro.getId_province(), p.getId_prize()).size() == 1}">
                                                <tr>
                                                    <c:forEach
                                                            items="${lottoService.getAllLottoByDPP(pro.getId_province(), p.getId_prize())}"
                                                            var="lotto">
                                                        ${lotto.adjustNumber()}
                                                        <td>${lotto.getNumber()}</td>
                                                    </c:forEach>
                                                </tr>
                                            </c:if>
                                            <c:if test="${lottoService.getAllLottoByDPP(pro.getId_province(), p.getId_prize()).size() > 1}">
                                                <tr>
                                                    <td>
                                                        <c:forEach
                                                                items="${lottoService.getAllLottoByDPP(pro.getId_province(), p.getId_prize())}"
                                                                var="lotto">
                                                            ${lotto.adjustNumber()}
                                                            <div>
                                                                    ${lotto.getNumber()}
                                                            </div>
                                                        </c:forEach>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </td>
                            </c:forEach>
                        </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"
        integrity="sha512-GsLlZN/3F2ErC5ifS5QtgpiJtWd43JWSuIgh7mbzZ8zBps+dvLusV+eNQATqgA/HdeKFVgA5v3S/cIrLF7QnIg=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<script>
    html2canvas(document.getElementById("xoso"), {
        onrendered: function (canvas) {
            var img = canvas.toDataURL("image/png");
            document.getElementById("png").setAttribute("href", " " + img + " ");
        }
    });

    function pdf() {
        var element = document.getElementById("xoso");
        html2pdf().set({filename: 'xoSoMienNam.pdf'}).from(element).save();
    }


    function tableToCSV() {
        let textTable = "";
        var arrTable = [];

        var col1 = document.getElementsByClassName('col');

        var tableChild11 = col1[0].getElementsByClassName('table-child1');

        var rows1 = tableChild11[0].getElementsByTagName('td');
        for (var j = 0; j < rows1.length; j++) {
            arrTable.push(rows1[j].innerHTML);
        }

        for (var i = 1; i < col1.length; i++) {
            var csv_child = [];
            var rows1 = col1[i].getElementsByTagName('td');
            for (var j = 0; j < rows1.length; j++) {
                if (rows1[j].children.length > 0) {
                    var divs = rows1[j].getElementsByTagName('div');
                    let number = "\"";
                    for (var d = 0; d < divs.length; d++) {
                        let text = divs[d].textContent;
                        if (d == divs.length - 1) {
                            number += text.replace("\n", "").trim() + "\"";
                        } else {
                            number += text.replace("\n", "").trim() + "\n";
                        }
                    }
                    csv_child.push(number);
                } else {
                    csv_child.push("\"\t" + rows1[j].textContent + "\"");
                }
            }
            for (var k = 0; k < csv_child.length; k++) {
                arrTable.push(csv_child[k]);
            }
        }

        var arr = [];
        for (var i = 0; i < col1.length; i++) {
            arr.push(arrTable.splice(0, 11));
        }

        for (var i = 0; i < 11; i++) {
            for (var j = 0; j < arr.length; j++) {
                if (j == arr.length - 1) {
                    textTable += arr[j][i];
                } else {
                    textTable += arr[j][i] + ",";
                }
            }
            if (i != 10) textTable += "\n";
        }

        console.log(textTable);
        downloadCSVFile(textTable);
    }

    function downloadCSVFile(csv_data) {

        // Create CSV file object and feed
        // our csv_data into it
        CSVFile = new Blob(["\ufeff", csv_data], {
            type: "text/csv;charset=utf-8"
        });

        // Create to temporary link to initiate
        // download process
        var temp_link = document.createElement('a');

        // Download csv file
        temp_link.download = "xoSoMienNam.csv";

        var url = window.URL.createObjectURL(CSVFile);
        temp_link.href = url;

        // This link should not be displayed
        temp_link.style.display = "none";
        document.body.appendChild(temp_link);

        // Automatically click the link to
        // trigger download
        temp_link.click();
        document.body.removeChild(temp_link);
    }

</script>
</body>
</html>
