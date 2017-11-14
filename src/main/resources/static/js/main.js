$(document).ready(function () {



});


var CopyToClipboard = function(text, fallback){
  var fb = function () {
    $t.remove();
    if (fallback !== undefined && fallback) {
      var fs = 'Please, copy the following text:';
      if (window.prompt(fs, text) !== null) return true;
    }
    return false;
  };
  var $t = $('<textarea />');
  $t.val(text).css({
    width: '100px',
    height: '40px'
  }).appendTo('body');
  $t.select();
  try {
    if (document.execCommand('copy')) {
      $t.remove();
      return true;
    }
    fb();
  }
  catch (e) {
    fb();
  }
};