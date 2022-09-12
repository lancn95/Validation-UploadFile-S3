let countDownSmsOtp;

function applyRedTextToSpan(text) {
    return `<span style="color: red">${text}</span>`
}

function disableButton(e) {
    $(e).attr("disabled", "disabled")
}

function enableButton(e, timeout) {
    setTimeout(() => {
        $(e).removeAttr("disabled")
    }, timeout);
}

function delayButton(e, time) {
    disableButton(e)
    enableButton(e, time || 2000)
}

// yyyy/MM/dd
function formatInstantToLocalDate(instant, delimer) {
    if (delimer == null) delimer = "-";
    if (instant === null) return "";
    let date = new Date(instant);
    let year = date.getFullYear();
    let month = addZeroToTime(date.getMonth() + 1);
    let day = addZeroToTime(date.getDate())
    return year + delimer + month + delimer + day;
}

// yyyyMM
function formatInstantToYearMonth(instant) {
    if (instant === null) return "";
    let date = new Date(instant);
    let year = date.getFullYear();
    let month = addZeroToTime(date.getMonth() + 1);
    return year + month;
}

// yyyy/MM
function formatInstantToYearMonth2(instant, delimer) {
    if (delimer == null) delimer = "-";
    if (instant === null) return "";
    let date = new Date(instant);
    let year = date.getFullYear();
    let month = addZeroToTime(date.getMonth() + 1);
    return year + delimer + month;
}

// MM/dd
function formatInstantToMonthDay(instant, delimer) {
    if (delimer == null) delimer = "-";
    if (instant === null) return "";
    let date = new Date(instant);
    let month = addZeroToTime(date.getMonth() + 1);
    let day = addZeroToTime(date.getDate())
    return month + delimer + day;
}

// yyyy/MM/dd hh:mm
function formatInstant(instant, delimer) {
    if (instant === null) return "";
    let date = new Date(instant);
    let result = formatInstantToLocalDate(instant, delimer);
    let hour = addZeroToTime(date.getHours())
    let minute = addZeroToTime(date.getMinutes())
    return result + " " + hour + ":" + minute;
}

// yyyy/MM/dd hh:mm:dd
function formatInstantWithSecond(instant, delimer) {
    if (instant === null) return "";
    let date = new Date(instant);
    let result = formatInstant(instant, delimer);
    let second = addZeroToTime(date.getSeconds())
    return result +  ":"  + second;
}

function format337(instant, delimer) {
    if (instant === null) return "";
    let date = new Date(instant);
    let result = formatInstantToLocalDate(instant, delimer);
    let hour = addZeroToTime(date.getHours())
    let minute = addZeroToTime(date.getMinutes())
    return result + "</br>" + hour + ":" + minute;
}

// yyyy/MM/dd 0:0:0
function getCurrentDateAtBeginningDay() {
    let date = new Date();
    date.setHours(0,0,0,0)
    return date;
}

function getStartTimeWithDayBefore(dayBefore){
    let date = new Date();
    date.setDate(date.getDate() - dayBefore);
    date.setHours(0,0,0,0)
    return date;
}



function getDateBefore(currentDate, dayBefore){
    let date = new Date(currentDate)
    date.setDate(date.getDate() - dayBefore);
    date.setHours(0,0,0,0)
    return date;
}

//yyyyyMMdd->yyyy-MM-dd
function formatStringDate(data) {
    if (data === null || !data) return "";
    let year = data.substring(0, 4);
    let month = data.substring(4, 6);
    let day = data.substring(6, 8);
    return year + "-" + month + "-" + day;
}

function addZeroToTime(time) {
    if (time < 10) return "0" + time;
    return time;
}

function shortenString(content, length) {
    if (content === null) return "";
    if (content.length <= length) return content;
    return content.substring(0, length) + "...";
}

function renderStringWithTooltip(content, length) {
    let shorten = shortenString(content, length);
    return `<span data-toggle="tooltip" title="${content}">${shorten}</span>`
}

let onSuccessDefault = function (result) {
    Swal.fire("Successfully!", "Updated!", "success");
}

let onErrorDefault = function (xhr) {
    let response = JSON.parse(xhr.responseText);
    Swal.fire("Error!", response.message !== 'No message available' ? response.message : "Error!", "error");
}

function ajaxGet(url, data, onSuccess, onError, onComplete) {
    $.ajax({
        url: url,
        method: "GET",
        contentType: 'application/json',
        data: data ? JSON.stringify(data) : null,
        success: onSuccess ? onSuccess : onSuccessDefault,
        error: onError ? onError : onErrorDefault,
        complete: onComplete
    });
}

function ajaxPost(url, data, onSuccess, onError, onComplete) {
    $.ajax({
        url: url,
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: onSuccess ? onSuccess : onSuccessDefault,
        error: onError ? onError : onErrorDefault,
        complete: onComplete
    });
}

function ajaxPostNoNotice(url, data, onSuccess, onError, onComplete) {
    $.ajax({
        url: url,
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: onSuccess
    });
}

function ajaxPut(url, data, onSuccess, onError, onComplete) {
    $.ajax({
        url: url,
        method: "PUT",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: onSuccess ? onSuccess : onSuccessDefault,
        error: onError ? onError : onErrorDefault,
        complete: onComplete
    });
}

function ajaxDelete(url, data, onSuccess, onError, onComplete) {
    $.ajax({
        url: url,
        method: "DELETE",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: onSuccess ? onSuccess : onSuccessDefault,
        error: onError ? onError : onErrorDefault,
        complete: onComplete
    });
}

function checkFieldRequired(data, header, message, type) {
    if (!data || data === "") {
        Swal.fire(header, message, type)
        return false;
    }
    return true;
}

function checkFieldRegex(data, regex, header, message, type) {
    if (!data || !data.match(regex)) {
        Swal.fire(header, message, type)
        return false;
    }
    return true;
}

function checkRequiredWithErrorText(data, errorArea, message) {
    if (!data || data === "") {
        errorArea.text(message)
        return false;
    }
    return true;
}

function checkRegexWithErrorText(data, regex, errorArea, message) {
    if (!data || !data.match(regex)) {
        errorArea.text(message)
        return false;
    }
    return true;
}

function popUpWithConfirm(message, yesCallback, title = "확인!") {
    Swal.fire({
        title: title,
        text: message,
        icon: "warning",
        showCancelButton: true,
        cancelButtonText: "취소",
        confirmButtonText: "예",
    }).then(function (result) {
        if (result.value) {
            yesCallback()
        }
    });
}

function successPopup(message) {
    Swal.fire("성공적으로!", message, "success")
}

function infoPopup(message) {
    Swal.fire("정보!", message, "info")
}

function errorPopup(message) {
    Swal.fire("오류!", message, "error")
}

function infoPopupCallback(message, callback) {
    Swal.fire("정보!", message, "info")
        .then(() => callback())
}

function successPopupCallback(message, callback) {
    Swal.fire("성공적으로!", message, "success")
        .then(() => callback())
}

function getDefaultValidationOptions(submitButtonId, fields) {
    return {
        fields: getFields(fields),
        plugins: {
            trigger: new FormValidation.plugins.Trigger(),
            bootstrap: new FormValidation.plugins.Bootstrap5({
                rowSelector: '.fv-row',
                eleInvalidClass: '',
                eleValidClass: ''
            }),
            submitButton: new FormValidation.plugins.SubmitButton({
                buttons: function (form) {
                    return [].slice.call(document.getElementById(submitButtonId));
                },
            }),
        }
    }
}

function getFields(fieldOptions) {
    let fields = {}
    for (let option of fieldOptions) {
        if(option[1] == ""){
            fields[option[0]] = {
                validators: {
                }
            }
        }else {
            fields[option[0]] = {
                validators: {
                    notEmpty: {
                        message: option[1]
                    }
                }
            }
        }
        if (option[2]) {
            fields[option[0]].validators.regexp = {
                message: option[2][1],
                regexp: option[2][0]
            }
        }
    }
    return fields;
}

let getUppyConfig = (config) => function () {
    config.files = new Map();
    const Tus = Uppy.Tus;
    const StatusBar = Uppy.StatusBar;
    const FileInput = Uppy.FileInput;
    const Informer = Uppy.Informer;

    let elemId = config.id || 'uppy_files';
    let id = '#' + elemId;
    let $statusBar = $(id + ' .uppy-status');
    let $uploadedList = $(id + ' .uppy-list');
    let timeout;
    let uppyMin = Uppy.Core({
        debug: true,
        autoProceed: true,
        showProgressDetails: true,
        restrictions: config.restrictions || {
            maxFileSize: 3000000, // 3mb
            maxNumberOfFiles: 1,
            minNumberOfFiles: 0
        }
    });

    config.uppy = uppyMin;

    uppyMin.use(FileInput, {target: id + ' .uppy-wrapper', pretty: false});
    uppyMin.use(Informer, {target: id + ' .uppy-informer'});

    uppyMin.use(Tus, {endpoint: 'https://master.tus.io/files/'});
    uppyMin.use(StatusBar, {
        target: id + ' .uppy-status',
        hideUploadButton: true,
        hideAfterFinish: false
    });

    $(id + ' .uppy-FileInput-input').addClass('uppy-input-control').attr('id', elemId + '_input_control');
    $(id + ' .uppy-FileInput-container').append('<label class="uppy-input-label btn btn-light-primary btn-sm btn-bold" for="' + (elemId + '_input_control') + '">파일첨부</label>');

    let $fileLabel = $(id + ' .uppy-input-label');

    uppyMin.on('upload', function (data) {
        $fileLabel.text("Uploading...");
        $statusBar.addClass('uppy-status-ongoing');
        $statusBar.removeClass('uppy-status-hidden');
        clearTimeout(timeout);
    });

    uppyMin.on('complete', function (file) {
        $.each(file.successful, function (index, value) {
            let sizeLabel = "bytes";
            let filesize = value.size;
            if (filesize > 1024) {
                filesize = filesize / 1024;
                sizeLabel = "kb";

                if (filesize > 1024) {
                    filesize = filesize / 1024;
                    sizeLabel = "MB";
                }
            }
            let uploadListHtml = '<div class="uppy-list-item" data-id="' + value.id + '"><div class="uppy-list-label">'
                + value.name + ' (' + Math.round(filesize, 2) + ' ' + sizeLabel + ')</div><span class="uppy-list-remove" data-id="'
                + value.id + '"><i class="flaticon2-cancel-music"></i></span></div>';
            $uploadedList.append(uploadListHtml);
            config.files.set(value.id, value.data)
        });

        $fileLabel.text("파일첨부");
        $statusBar.addClass('uppy-status-hidden');
        $statusBar.removeClass('uppy-status-ongoing');
    });

    $(document).on('click', id + ' .uppy-list .uppy-list-remove', function () {
        let itemId = $(this).attr('data-id');
        uppyMin.removeFile(itemId);
        $(id + ' .uppy-list-item[data-id="' + itemId + '"').remove();
        config.files.delete(itemId)
    });
};

function getDefaultUppyRestrictions() {
    return {
        maxFileSize: 3000000, // 3mb
        maxNumberOfFiles: 1,
        minNumberOfFiles: 0
    }
}

function addParamsToFormData(formData, arr) {
    arr.forEach(e => formData.append(e, $("#" + e).val()))
}

function getValueWithOption(isFirst, first, second) {
    if (isFirst) return $("#" + first).val();
    return $("#" + second).val();
}

function renderOptions(element, data) {
    let options = data.reduce((sum, element) => sum + `<option value="${element.value}">${element.html}</option>`, "")
    $("#" + element).html(options);
}

function intVal (i) {
    return typeof i === 'string'
        ? i.replace(/[\$,]/g, '') * 1
        : typeof i === 'number' ? i : 0;
}

function getValueByKey(arr, data , defaultValue){
    for (let element of arr){
        if (data === element[0]) return element[1]
    }
    return defaultValue || ""
}

function defaultResetValue(element, defaultValue ){
    $("#" + element).val(defaultValue || "")
}

function countDown(minute, idDisplay) {
    let time = 60 * minute
    let display = document.querySelector("#" + idDisplay);
    clearInterval(countDownSmsOtp)
    countDownSmsOtp = startTimer(time, display);
};

function startTimer(duration, display) {
    let timer = duration, minutes, seconds;
    return setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = minutes + ":" + seconds;

        if (--timer < 0) {
            timer = 0;
        }
    }, 1000);
}

function changeColorOnHover(dom, onOver, onLeave){
    dom.on('mouseover', function() {
        $(this).css("color", onOver)
    })

    dom.on('mouseleave', function() {
        $(this).css("color", onLeave)
    })
}

function getColors(number){
    let colorArray = ['#4baae9', '#0d9109', '#ff6633', '#e6331a', '#00B3E6',
        '#E6B333', '#FFB399', '#999966', '#99FF99', '#B34D4D',
        '#80B300', '#809900', '#E6B3B3', '#6680B3', '#66991A',
        '#FF99E6', '#CCFF1A', '#FF1A66', '#33FFCC', '#CC80CC',
        '#66994D', '#B366CC', '#4D8000', '#B33300', '#FFFF99',
        '#66664D', '#991AFF', '#E666FF', '#4DB3FF', '#1AB399',
        '#E666B3', '#33991A', '#CC9999', '#B3B31A', '#00E680',
        '#4D8066', '#809980', '#E6FF80', '#1AFF33', '#999933',
        '#FF3380', '#CCCC00', '#66E64D', '#4D80CC', '#9900B3',
        '#E64D66', '#4DB380', '#FF4D4D', '#99E6E6', '#6666FF'];
    let result = []
    for (let i = 0; i < number; i++) {
        result.push(colorArray[i])
    }
    return result;
}

function getSameDayLastMonth(n, date) {
    let startMonth = date != null ? date : new Date();
    startMonth.setMonth(startMonth.getMonth() - n);
    return startMonth
}

function setSummaryTable(json, id1, id2, id3, id4, id5){
    if (json.cancellationAmount === null || json.cancellationAmount === NaN) json.cancellationAmount = 0
    if (json.approvalAmount === null || json.approvalAmount === NaN) json.approvalAmount = 0
    let difference = intVal(json.approvalAmount) - intVal(json.cancellationAmount)
    id1.text(json.numberOfApprovals.toLocaleString("en-US"));
    id2.text(json.approvalAmount.toLocaleString("en-US") + "원");
    id3.text(json.numberOfCancellations.toLocaleString("en-US"));
    if(json.cancellationAmount != 0){
        id4.text("-" + json.cancellationAmount.toLocaleString("en-US") + "원");
    }
    id5.text(difference.toLocaleString("en-US") + "원");
}