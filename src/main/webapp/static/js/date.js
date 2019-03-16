
function genTodayStr(){
	var date = new Date();
	return date.getFullYear()+"-"+formatZero(date.getMonth()+1)+"-"+formatZero(date.getDate());
}

function genLastYearStr(){
	var date = new Date();
	date.setFullYear(date.getFullYear()-1);
	return date.getFullYear()+"-"+formatZero(date.getMonth()+1)+"-"+formatZero(date.getDate());
}

function genLastMonthDayStr(){
	var date = new Date();
	var month;
	var year;
	if (date.getMonth() == 0) {
		month=12;
		year = date.getFullYear()-1;
	}else {
		month=(date.getMonth())<10?"0"+(date.getMonth()):(date.getMonth());
		year = date.getFullYear();
	}
	return year+"-"+month+"-"+formatZero(date.getDate());
}

function genLastWeekDayStr(){
	var date = new Date();
	date.setDate(date.getDate()-6);
	return date.getFullYear()+"-"+formatZero(date.getMonth()+1)+"-"+formatZero(date.getDate());
}

function formatZero(n){
	if(n<10){
		return "0"+n;
	}else{
		return n;
	}
}