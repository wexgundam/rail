var TableDatatablesEditable = function () {

    var handleTable = function () {
        
    	var count = 0;
    	
        function restoreRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);

            for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                oTable.fnUpdate(aData[i], nRow, i, false);
            }

            oTable.fnDraw();
        }
    
        function editRow(oTable, nRow) {
            var aData = oTable.fnGetData(nRow);
            var jqTds = $('>td', nRow);
        	jqTds[0].innerHTML = '<input type="text" prev='+$(jqTds[0]).attr("prev")+' id="code'+count+'" maxlength="20" name="code"  class="form-control input-small" value="' + aData[0] + '">';
            jqTds[1].innerHTML = '<input type="text" prev='+$(jqTds[1]).attr("prev")+' id="name'+count+'" prev='+aData[1]+' maxlength="20" name="name" class="form-control input-small" value="' + aData[1] + '">';
            jqTds[2].innerHTML = '<input type="text" prev='+$(jqTds[2]).attr("prev")+' id="displayOrder'+count+'" name="displayOrder" maxlength="6" class="form-control input-small" value="' + aData[2] + '">';
            jqTds[3].innerHTML = '<a class="edit" href="">保存</a>';
            jqTds[4].innerHTML = '<a class="delete" href="">删除</a>';
            count++;
        }

        function saveRow(oTable, nRow) {
            var jqInputs = $('input[name!=id]', nRow);
            var flag1,flag2;
        	/*console.log(jqInputs);
        	console.log(jqInputs[0].value);
        	console.log(jqInputs[1].value);
        	console.log(jqInputs[2].value);
        	//console.log((isNaN(jqInputs[2].value!='' &&　jqInputs[2].value!=' ' && jqInputs[2].value)));*/
            
            //点击新增后没有输入
        	if(jqInputs[0].value=='' ||　jqInputs[1].value=='' || (isNaN(jqInputs[2].value!='' &&　jqInputs[2].value!=' ' && jqInputs[2].value)) ||　jqInputs[2].value==''){
        		oTable.fnUpdate('<a class="edit" href="">保存</a>', nRow, 3, false);
                oTable.fnUpdate('<a class="delete" href="">删除</a>', nRow, 4, false);
        		return false;
        	}
        	
            flag1 = $(jqInputs[0]).parent().attr("validate");
            flag2 = $(jqInputs[1]).parent().attr("validate");
            
            if(typeof(flag1) == "undefined"){
            	flag1 = true;
            }
            
            if(typeof(flag2) == "undefined"){
            	flag2 = true;
            }
            
            //输入的内容不符合规则
        	if(!(eval(flag1) && eval(flag2))){
        		oTable.fnUpdate('<a class="edit" href="">保存</a>', nRow, 3, false);
                oTable.fnUpdate('<a class="delete" href="">删除</a>', nRow, 4, false);
        		return false;
        	}
            
            oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
            oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
            oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
            oTable.fnUpdate('<a class="edit" href="">修改</a>', nRow, 3, false);
            oTable.fnUpdate('<a class="delete" href="">删除</a>', nRow, 4, false);
            oTable.fnDraw();
            return true;
        }

        function cancelEditRow(oTable, nRow) {
            var jqInputs = $('input[name!=id]', nRow);
            oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
            oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
            oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
            oTable.fnUpdate('<a class="edit" href="">修改</a>', nRow, 3, false);
            oTable.fnDraw();
        }
        

        var table = $('#sample_editable_1');

        var oTable = table.dataTable({

           
        	"searching" : false, //去掉搜索框方法一：百度上的方法，但是我用这没管用
            
            "bLengthChange": false,   //去掉每页显示多少条数据方法
            "bPaginate":false,
            "info":false,
            "bSort" : false,

           "language": {
                "lengthMenu": " _MENU_ records"
            },
           /* "columnDefs": [{ // set default column settings
                'orderable': false,
                'targets': [0]
            }, {
                "searchable": true,
                "targets": [0]
            }],*/
            /*"aaSorting": [
                [0, "desc"]
            ] ,*/
        });
        
        

        var tableWrapper = $("#sample_editable_1_wrapper");

        var nEditing = null;    //用于标记正在编辑的行数据
        var nNew = false;       //是不是新增的一行
        
        

        $('#sample_editable_1_new').click(function (e) {
            e.preventDefault();
            
            //判断之前新增的一行是否需要保存
            /* if (nNew && nEditing) { 
                if (confirm("之前新增的字典未保存. 您想要保存吗?")) {
                	saveRow(oTable, nEditing); // save
                    nEditing = null;
                    nNew = false;

                } else {
                    oTable.fnDeleteRow(nEditing); // cancel
                    nEditing = null;
                    nNew = false;
                    return;
                }
            }*/
            
            //新增一行
            var sysDicAttr = ["code","name","displayOrder"];
            var aiNew = oTable.fnAddData(['', '', '','','']);
            var nRow = oTable.fnGetNodes(aiNew[0]);
           
            editRow(oTable, nRow);
            nEditing = nRow;
            nNew = true;
            
            $('table tr:eq('+(aiNew[0]+1)+') input').each(function(i){
            	$(this).parent().parent().addClass("newRow");
                $(this).attr("name",sysDicAttr[i]);
                $(this).parent().parent().children("td:eq(0)").find("input").attr("maxlength",20);
                $(this).parent().parent().children("td:eq(1)").find("input").attr("maxlength",20);
                $(this).parent().parent().children("td:eq(2)").find("input").attr("maxlength",6);
                $(this).attr("id",sysDicAttr[i]+count);
                count++;
            });
        });

        
        //删除一行
        table.on('click', '.delete', function (e) {
            e.preventDefault();
            var nRow = $(this).parents('tr')[0];
            var that = $(this).parents('tr').find("input");
            
            var delSys = {id:that.val(),category:"",categoryName:"",code:"",name:"",displayOrder:""};
            		
            bootbox.confirm("你确定要删除该字典信息吗？", function (result) {
                if (result) {
                	oTable.fnDeleteRow(nRow);
                	if(that.val()!=null && that.val()!='' &&　typeof(that.val())!='undefined'){
                		delArray.push(delSys);
                    }
                } 
           })
        });

        
        table.on('click', '.cancel', function (e) {
            e.preventDefault();
            if (nNew) {
                oTable.fnDeleteRow(nEditing);
                nEditing = null;
                nNew = false;
            } else {
                restoreRow(oTable, nEditing);
                nEditing = null;
            }
        });

        
        /**
         * 点击修改，修改完成后：
         * 1、完成数据校验，正常保存，保存状态修改为修改
         * 2、没有完成数据校验，不修改保存状态
         * 
         */
        table.on('click', '.edit', function (e) {
            e.preventDefault();
            
            nNew = false;
            var nRow = $(this).parents('tr')[0];
            //若不符合条件则不能保存
            if ((this.innerHTML).trim() == "保存") {
                var result = saveRow(oTable, nRow);
                if(!result){
                	saveRow(oTable, nRow);
                }
             }else if((this.innerHTML).trim() == "修改"){
	            editRow(oTable, nRow);
             }
        });
 

    }

    return {

        //main function to initiate the module
        init: function () {
            handleTable();
        }

    };

}();

jQuery(document).ready(function() {
    TableDatatablesEditable.init();
});