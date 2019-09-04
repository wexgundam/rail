<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../common/taglib.jsp" %>
<html lang="en">
<head>
    <title>修改密码</title>
    <style>

        .vali_pass_progress {
            background-color: #efefef;
            height: 20px;
            border-radius: 5px;
        }

        .vali_pass_inner_progress {
            display: block;
            height:100%;
            background-color: transparent;
            border-radius: 5px;

        }
        .error {
            background-color: #efefef;

        }
        .middle {
            background-color: gold;

        }
        .strong {
            background-color: green;

        }
    </style>
</head>

<body>

<div class="page-bar">
    <ul class="page-breadcrumb">
        <li>
            <i class="fa fa-home"></i> <a href="${dynamicServer}/index.htm">首页</a>
        </li>
        <li>
            >修改密码
        </li>
    </ul>
</div>
<h1 class="page-title"> 修改密码
</h1>


<div class="row">
    <div class="col-md-12">
        <form id="userForm" name="userForm" class="form-horizontal" action="saveUpdatePass.htm" method="post">
            <input type="hidden" name="backUrl" value="${backUrl }">
            <div class="form-body">
                <div class="form-group">
                    <label class="col-md-3 control-label">原密码：</label>
                    <div class="col-md-9">
                        <input id="oldPass" name="oldPass" type="password"
                               class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value=""> <label
                            id="oldPassTip"></label>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">新密码：</label>
                    <div class="col-md-9">
                        <input id="newPass" type="password" name="newPass"
                               class="form-control input-inline input-xlarge "
                               placeholder=""
                               value=""><label
                            id="newPassTip"></label>
                        <span class="help-block input-xlarge">
                            <div class="col-md-3" id="strWeak"
                                    style="border-radius: 5px;background-color: #efefef;display: block;height:20px; "></div>
                            <div class="col-md-3" id="strMedium"
                                 style="border-radius: 5px;background-color: #efefef;display: block;height:20px; "></div>
                            <div class="col-md-3" id="strStrong"
                                 style="border-radius: 5px;background-color: #efefef;display: block;height:20px; "></div>
                            <div class="col-md-3" id ='strText'>无</div>

                        </span>
                   </div>
                </div>



                <div class="form-group">
                    <label class="col-md-3 control-label">确认新密码：</label>
                    <div class="col-md-9">
                        <input id="renewPass" type="password" name="renewPass"
                               class="form-control input-inline  input-xlarge"
                               placeholder=""
                               value=""><label
                            id="renewPassTip"></label>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <div class="row">
                    <div class="col-md-offset-3 col-md-9">
                        <button type="submit" class="btn btn-primary"><i class="fa fa-save"/></i> 保存</button>
                        <button type="button" class="btn default" onclick="history.back(-1)"><i
                                class="fa fa-undo"/></i>  取消
                        </button>
                    </div>
                </div>
            </div>

        </form>

    </div>
</div>
</body>

<critc-script>
    <script type="text/javascript">
        $(function () {
            var validate = $("#userForm").validate({
                errorElement: "label",
                errorClass: "valiError",
                errorPlacement: function (error, element) {
                    error.appendTo($("#" + element.attr('id') + "Tip"));
                },
                rules: {
                    oldPass: {
                        required: true,
                        minlength: 6,
                        maxlength: 20
                    },
                    newPass: {
                        required: true,
                        minlength: 6,
                        maxlength: 20
                    },
                    renewPass: {
                        required: true,
                        minlength: 6,
                        maxlength: 20,
                        equalTo: "#newPass"
                    },
                },
                messages: {
                    renewPass: {
                        equalTo: "确认新密码与新密码输入不一致"
                    }
                },
                submitHandler: function (form) {
                    form.submit();
                }
            });
            $("#newPass").keydown(function () {
                // alert('---------');
                //密码为八位及以上并且字母数字特殊字符三项都包括
                var strongRegex = new RegExp("^(?=.{8,})((?=.*[A-Z])|(?=.*[a-z]))(?=.*[0-9])(?=.*\\W).*$", "g");
                //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等
                var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
                if (strongRegex.test($(this).val())) {
                    $("#strStrong").css('background-color',"green");
                    $("#strMedium").css('background-color',"#efefef");
                    $("#strWeak").css('background-color',"#efefef");
                    $("#strText").text('强')
                } else  if (mediumRegex.test($(this).val())) {
                    $("#strMedium").css('background-color',"gold");
                    $("#strStrong").css('background-color',"#efefef");
                    $("#strWeak").css('background-color',"#efefef");
                    $("#strText").text('中')
                }else{
                    $("#strWeak").css('background-color',"red");
                    $("#strStrong").css('background-color',"#efefef");
                    $("#strMedium").css('background-color',"#efefef");
                    $("#strText").text('弱')
                }
            });
        });

    </script>

</critc-script>
</html>
