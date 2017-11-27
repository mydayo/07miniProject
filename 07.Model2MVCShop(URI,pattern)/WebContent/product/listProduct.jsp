<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<title> ��ǰ �����ȸ</title>


<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
<!--
function fncGetList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
-->

</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/product/listProduct?menu=${menu}" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					${requestScope.menu == 'search' ? "��ǰ�����ȸ" : "��ǰ����"}

					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>

		<td align="right">
		
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��</option>
				<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>��ǰ����</option>
			</select>
			
			<input 	type="text" name="searchKeyword" 
			value="${search.searchKeyword}" 
			 class="ct_input_g" style="width:200px; height:20px" >
							
		</td>
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetList('1');">�˻�</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="08" >
		��ü  ${resultPage.totalCount } �Ǽ�, ���� ${resultPage.currentPage}  ������
		</td>
		
		<td colspan="03" align = "right">
		<!-- �������� -------------------------------------------------------------------------------------------------------- -->
		<c:if test = "${menu == 'search'}">
		��<a href="/product/listProduct?prodNo=${product.prodNo}&menu=search&priceList=DESC"> ���� ���� �� </a>
		</c:if>
		
		<c:if test = "${menu == 'search'}">
		��<a href="/product/listProduct?prodNo=${product.prodNo}&menu=search&priceList=ASC"> ���� ���� �� </a>
		</c:if>
		
		<c:if test = "${menu == 'manage'}">
		��<a href="/product/listProduct?prodNo=${product.prodNo}&menu=manage&priceList=DESC"> ���� ���� �� </a>
		</c:if>
		
		<c:if test = "${menu == 'manage'}">
		��<a href="/product/listProduct?prodNo=${product.prodNo}&menu=manage&priceList=ASC"> ���� ���� �� </a>
		</c:if>
		
		<!-- -------------------------------------------------------------------------------------------------------- -->
		</td>
		
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">������</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>	
	</tr>

	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:set var = "i" value = "0" />
	<c:forEach var = "product" items = "${listProduct}">
		<c:set var = "i" value = "${i+1}" />
		<tr class="ct_list_pop">
		<td align="center">${ i }</td>
			<td></td>
		<!-- �̹��� ���� -->
		<td align = "center"> 
	
   
    	<c:if test = "${empty product.fileName}">
    		<img src="/images/error/noImage.jpg" height = "150" width = "150"/>
    	</c:if>
    	
    	<c:if test = "${!empty product.fileName}">
    		<img src="/images/uploadFiles/${product.fileName}" height = "150" width = "150"/>
    	</c:if>
    		
		<td></td>

		
		
		<!-- �̹��� ���� -->
			<td align="left">
			<c:if test = "${empty product.proTranCode}">
				
			 	<c:if test = "${menu == 'search'}">
					 <a href="/product/getProduct?prodNo=${product.prodNo}&menu=search">${product.prodName}</a>
				 </c:if>
				 
			 	<c:if test = "${menu == 'manage'}">
				 	 <a href="/product/updateProductView?prodNo=${product.prodNo}&menu=manage">${product.prodName}</a>
				 </c:if>
				 
			</c:if>
			
			<c:if test = "${! empty product.proTranCode}">
			${product.prodName}
			</c:if>
			
			 </td>
			
			
			<td></td>
		<td align="left">${product.price}</td>
		<td></td>
		<td align="left">${product.manuDate}</td>
		<td></td>
		<td align="left">
			
			
		<!--��Ǭ���� ----------------------------------------------------------- -->
		  <c:if test = "${requestScope.menu == 'search'}">
		  	
		  	<c:if test = "${sessionScope.user.role == 'admin'}">
		  			
				<c:if test = "${empty product.proTranCode }">
					�Ǹ���
				</c:if>
				
				<c:if test = "${product.proTranCode.trim() == '01'}">
					���ſϷ� 
				</c:if>
			
				<c:if test = "${product.proTranCode.trim() == '02'}">
					����� 
				</c:if>
				
				<c:if test = "${product.proTranCode.trim() == '03'}">
					��ۿϷ�
				</c:if>
				
			</c:if>
			
			
			
			
			<c:if test = "${sessionScope.user.role == 'user'}">
		  			
		  			
		  		${ ! empty product.proTranCode ? "������" : "�Ǹ���" }
		  		
			
			</c:if>
			
		  </c:if>
		  
		 <!--�ǸŻ�ǰ���� ----------------------------------------------------------- -->
			    <c:if test = "${requestScope.menu == 'manage'}">
			  
					<c:if test = "${product.proTranCode == null }">
						�Ǹ���  
					</c:if>
				
				  <c:if test = "${product.proTranCode.trim() == '01' }">	
					<c:if test = "${! empty product.proTranCode}">
					
						���ſϷ�  <a href="/purchase/updateTranCode?prodNo=${product.prodNo}&proTranCode=${product.proTranCode.trim()}
																&search=${search}&menu=manage">����ϱ�</a>
					</c:if>
				  </c:if>
				  
				  <c:if test = "${product.proTranCode.trim() == '02' }">	
					<c:if test = "${! empty product.proTranCode}">
						�����
					</c:if>
				  </c:if>
				  
				   <c:if test = "${product.proTranCode.trim() == '03' }">	
					<c:if test = "${! empty product.proTranCode}">
						��ۿϷ�  
					</c:if>
				  </c:if>
				  
		  		</c:if>
			
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	</c:forEach>
</table>


<!-- PageNavigation Start... -->
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
		   <input type="hidden" id="menu" name="menu" value="${menu}"/>
		   <input type="hidden" id="priceList" name ="priceList" value = "${priceList}">
		  <jsp:include page="../common/pageNavigator.jsp"/>	
		
    	</td>
	</tr>
</table>

<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>