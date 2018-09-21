var Birthday = {

  init: function() {
    var currentMonth = (new Date()).getMonth() + 1;
    var currentDay = (new Date()).getDate();

    $("[name='month']").val(currentMonth);
    $("[name='day']").val(currentDay);

    $("#nextBirthday").prop("checked", true);

    // Register event handlers
    $("#birthday-submit").click(function() {
      var day = $("[name='day']").val();
      var leap = ($("#leap").prop("checked") ? "L" : "");
      var month = $("[name='month']").val() + leap;

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

    // Register popover
    $("#leap-popover").popover();
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