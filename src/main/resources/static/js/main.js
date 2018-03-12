var Birthday = {

  init: function() {

    /*
     TODO
     - set focus in month box on focus
     - set month and day to today's
    */

    var currentDate = new Date();
    var currentYear = (new Date()).getFullYear();
    var currentMonth = (new Date()).getMonth() + 1;
    var currentDay = (new Date()).getDate();

    $('[name="year"]').val(currentYear);
    $('[name="month"]').val(currentMonth);
    $('[name="day"]').val(currentDay);

    $("#birthday-submit").click(function() {
      Birthday.submit();
    });
  },

  submit: function() {
    $.get("/birthday", $('#birthday-form').serialize())
      .done(function(data){
        $("#single-birthday-result .result").html(data);
        $("#single-birthday-result").slideDown("slow", function() { });
      });
  }

};

$(document).ready(function() {
  Birthday.init();
});