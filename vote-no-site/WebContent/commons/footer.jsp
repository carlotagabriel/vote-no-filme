<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

            </div>
            
<script type="text/javascript">
function replaceContext(path){
    $.ajax({
        url: path,
        type: "GET",
        success: function(msg){
             $('#main-content').html(msg);
        }
    });
}
</script>
</body>
</html>
