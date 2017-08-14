/**
 * Created by huanglin on 2017/8/14.
 */
$(function() {
    bsStep(3)
    //bsStep(i) i 为number 可定位到第几步 如bsStep(2)/bsStep(3)


    function bsStep(i) {
        $('.step').each(function() {
            var a, $this = $(this);
            if(i > $this.find('li').length) {
                console.log('进度条数超过' + $this.find('li').length );
                a=$this.find('li').length;
            } else if(i == undefined && $this.data('step') == undefined) {
                a = 1
            } else if(i == undefined && $this.data('step') != undefined) {
                a = $(this).data('step');
            } else {
                a = i
            }
            $(this).find('li').removeClass('active');
            $(this).find('li:lt(' + a + ')').addClass('active');
        })
    }

})
