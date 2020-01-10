<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>微信支付-native</title>
</head>
<body>
<div id="qrcode"></div>
<input type="hidden" name="orderId" id="orderId" value="${orderId}" />
<input type="hidden" name="returnUrl" id="returnUrl" value="${returnUrl}" />
<script src="https://cdn.bootcss.com/jquery/1.5.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
<script>
    jQuery(function(){
        jQuery('#qrcode').qrcode({
            text:"${codeUrl}"
        });
    })

   $(function () {
       // 定时器
       const time = setInterval(function () {
            $.ajax({
                'url':'http://xbd.free-http.svipss.top/pay/queryByOrderId',
                data:{
                    'orderId':$("#orderId").val()
                },
                success:function (result) {
                    console.log(result);
                    if (result.platformStatus != null && result.platformStatus === "SUCCESS"){
                        clearInterval(time);
                        window.location.href = $("#returnUrl").val();
                    }
                },error:function (result) {
                    alert(result)
                }
            });
       },2000)
   })
</script>
</body>
</html>