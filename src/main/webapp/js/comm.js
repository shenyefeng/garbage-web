var ComAxCtrl = null;
var isRecevied = false;
function readcomm() {
	isRecevied = false;
	var flag = false;
	var flag1 = false;
	if (ComAxCtrl == null) {
		flag = true;
	}
	if (flag) {
		ComAxCtrl = $("#ComAxCtrl")[0];
		ComAxCtrl.attachEvent("OnCommRecv", OnCommRecv);
		flag1 = true;
	} else {
		if (!ComAxCtrl.IsCommOpen()) {
			flag1 = true;
		}
	}
	if (flag1) {
		var result = ComAxCtrl.CommOpen(9, "9600,n,8,1");
		if (result == 0) {
			alert(dictionary['label.scaleDoesnotExist']);
			return false;
		}
	}
	if (!ComAxCtrl.IsCommOpen()) {
		alert(dictionary['label.scaleDoesnotOpen']);
		return false;
	}

	// 发送命令
	ComAxCtrl.CommSend(stringToHex("TPLT"));
}

function OnCommRecv(data) {
	if (!isRecevied) {
		isRecevied = true;
		hexToString(data);
	} else {
		ComAxCtrl.CommClose();
	}
}

function stringToHex(str) {
	var val = "";
	for (var i = 0; i < str.length; i++) {
		if (val == "")
			val = "0X" + str.charCodeAt(i).toString(16);
		else
			val += ",0X" + str.charCodeAt(i).toString(16);
	}
	return val;
}
function hexToString(str) {
	var val = "";
	var arr = str.split(" ");
	for (var i = 0; i < arr.length; i++) {
		val += String.fromCharCode("0x" + arr[i]);
	}
	val = val.replace(/\r/g, "");
	val = val.replace(/\n/g, "");
	val = val.replace(/ /g, "");
	if (val.split("kg").length > 2) {
		val = val.split("kg")[1];
	} else if (val.split("kg").length == 2 && val.indexOf("+") == 0) {
		val = val.split("kg")[0];
	} else {
		val = "";
		isRecevied = false;
	}
	$(".read-weight").val(val);
}