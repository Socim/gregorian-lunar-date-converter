var Birthday = {

  init: function() {
    var currentMonth = (new Date()).getMonth() + 1;
    var currentDay = (new Date()).getDate();
    var currentYear = (new Date()).getFullYear();

    $("[name='month']").val(currentMonth);
    $("[name='day']").val(currentDay);
    $('[name="year"]').val(currentYear);

    $("#nextBirthday").prop("checked", true);

    // Register event handlers
    $("#birthday-submit").click(function() {
      var day = $("#birthday-form [name='day']").val();
      var leap = ($("#birthday-form [name='leap']").prop("checked") ? "L" : "");
      var month = $("#birthday-form [name='month']").val() + leap;

      if ($("#recurringBirthday").prop("checked") &&
          $("#personName").val() !== "") {
        Birthday.getBirthdayICal(month, day);
      } else {
        Birthday.getBirthday(month, day);
      }
    });

    $('input:radio[name="birthdayRadio"]').change(function(){
      $("#personNameDiv").toggle();
    });

    $("#greg-to-lunar-submit").click(function() {
      var day = $("#greg-to-lunar-form [name='day']").val();
      var month = $("#greg-to-lunar-form [name='month']").val();
      var year = $("#greg-to-lunar-form [name='year']").val();

      $.get("/date/lunar/" + year + "/" + month + "/" + day)
        .done(function(data){
          var result;
          if (data == "") {
            result = "Something went wrong."
          } else {
            result = "The lunar date is: " + data;
          }

          $("#greg-to-lunar-result .result").html(result);
          $("#greg-to-lunar-result").slideDown("slow", function() { });
        });
    });

    $("#lunar-to-greg-submit").click(function() {
      var day = $("#lunar-to-greg-form [name='lday']").val();
      var leap = ($("#lunar-to-greg-form [name='leap']").prop("checked") ? "L" : "");
      var month = $("#lunar-to-greg-form [name='lmonth']").val() + leap;
      var year = $("#lunar-to-greg-form [name='lyear']").val();

      $.get("/date/gregorian/" + year + "/" + month + "/" + day)
        .done(function(data){
          var result;
          if (data == "") {
            result = "Something went wrong. Is your date valid?"
          } else {
            result = "The gregorian date is: " + data;
          }

          $("#lunar-to-greg-result .result").html(result);
          $("#lunar-to-greg-result").slideDown("slow", function() { });
        });
    });

    // Register popover
    $(".popover-btn").popover();
  },

  getBirthday: function(month, day) {
    $.get("/birthday/" + month + "/" + day)
      .done(function(data){
        var result;
        if (data == "") {
          result = "Something went wrong. Is your date correct?"
        } else {
          result = "The next gregorian birthday is " + data;
        }

        $("#single-birthday-result .result").html(result);
        $("#single-birthday-result").slideDown("slow", function() { });
      });
  },

  getBirthdayICal: function(month, day) {
    var birthdayNameEncoded = encodeURIComponent($("#personName").val());
    window.location.href = "ical/birthday/" + month + "/" + day + "?birthdayPerson=" + birthdayNameEncoded;
  }

};

$(document).ready(function() {
  Birthday.init();
});