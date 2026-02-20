import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil

// Variables injected per row from Student Data Scenario.csv:
// Scenario, ExpectedResult, FirstName, LastName, StudentEmail,
// MobileNumber, DateOfBirth, CurrentAddress, State, City

WebUI.openBrowser('')
WebUI.navigateToUrl('https://demoqa.com/')
WebUI.maximizeWindow()
WebUI.waitForPageLoad(10)

WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/card_elements'), 5)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUI.findWebElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/card_elements'))))
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUI.findWebElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/menu_forms'))))
WebUI.delay(1)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUI.findWebElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/menu_practiceForm'))))
WebUI.waitForPageLoad(5)

WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/label_Male_custom-control-label'), 1)

if (FirstName)  WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Name_firstName'), FirstName)
if (LastName)   WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Name_lastName'), LastName)
if (StudentEmail) WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Email_userEmail'), StudentEmail)

// TC005_NoGender: skip gender click; semua lainnya pilih Male
if (!Scenario.startsWith('TC005')) {
    WebUI.executeJavaScript("document.querySelector('label[for=gender-radio-1]').click();", null)
}

if (MobileNumber) WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_(10 Digits)_userNumber'), MobileNumber)

// TC009_EmptyAllFields: tidak ada data apapun, langsung submit
if (Scenario.startsWith('TC009')) {
    WebUI.executeJavaScript('window.scrollBy(0, 300)', null)
    WebUI.executeJavaScript("var ads=document.querySelectorAll('.ad-container,.close-button,#adplus-anchor,[id*=google_ads],[class*=adsbygoogle]'); ads.forEach(function(a){a.remove();});", null)
    WebUI.executeJavaScript("document.getElementById('submit').click();", null)
    String popupTC9 = WebUI.executeJavaScript("return document.getElementById('example-modal-sizes-title-lg') ? document.getElementById('example-modal-sizes-title-lg').innerText.trim() : '';", null)
    boolean modalTC9 = popupTC9.equals('Thanks for submitting the form')
    WebUI.takeScreenshot()
    WebUI.closeBrowser()
    if (!modalTC9) {
        KeywordUtil.markPassed('[TC009_EmptyAllFields] Form tidak tersubmit saat semua field kosong — PASSED')
    } else {
        KeywordUtil.markFailed('[TC009_EmptyAllFields] Form tersubmit padahal semua field kosong — FAILED')
    }
    return
}

String[] dobParts = DateOfBirth.split('-')
String dobYear  = dobParts[0]
String dobMonth = String.valueOf(Integer.parseInt(dobParts[1]) - 1)
String dobDay   = String.valueOf(Integer.parseInt(dobParts[2]))
WebUI.executeJavaScript("document.getElementById('dateOfBirthInput').click();", null)
WebUI.executeJavaScript("document.querySelector('.react-datepicker__year-select').value='" + dobYear + "'; document.querySelector('.react-datepicker__year-select').dispatchEvent(new Event('change',{bubbles:true}));", null)
WebUI.executeJavaScript("document.querySelector('.react-datepicker__month-select').value='" + dobMonth + "'; document.querySelector('.react-datepicker__month-select').dispatchEvent(new Event('change',{bubbles:true}));", null)
WebUI.executeJavaScript("var days=document.querySelectorAll('.react-datepicker__day:not(.react-datepicker__day--outside-month)'); for(var d of days){if(d.textContent.trim()==='" + dobDay + "'){d.click();break;}}", null)

WebUI.delay(1)
// TC008_InvalidMobileAlpha: Subjects & Hobbies tetap diisi, hanya Mobile yang berisi huruf
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Subjects_subjectsInput'), 'A')
WebUI.executeJavaScript("document.querySelector('[id^=react-select-2-option]').click();", null)
WebUI.executeJavaScript("document.querySelector('label[for=hobbies-checkbox-3]').click();", null)

WebUI.uploadFile(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Select picture_uploadPicture'), RunConfiguration.getProjectDir() + '/Data Files/student_photo.jpg')
WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/textarea_Current Address_currentAddress'), 3)
if (CurrentAddress) WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/textarea_Current Address_currentAddress'), CurrentAddress)

WebUI.executeJavaScript('window.scrollBy(0, 300)', null)
WebUI.delay(1)
WebUI.executeJavaScript("var ads=document.querySelectorAll('.ad-container,.close-button,#adplus-anchor,[id*=google_ads],[class*=adsbygoogle]'); ads.forEach(function(a){a.remove();});", null)
WebUI.delay(1)

// TC007_MaxlengthMobile: verifikasi attribute maxlength, tidak perlu submit
if (Scenario.startsWith('TC007')) {
    String maxLength = WebUI.getAttribute(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_(10 Digits)_userNumber'), 'maxlength')
    boolean maxOk = maxLength == '10'
    WebUI.takeScreenshot()
    WebUI.closeBrowser()
    if (maxOk) {
        KeywordUtil.markPassed('[' + Scenario + '] maxlength=10 verified')
    } else {
        KeywordUtil.markFailed('[' + Scenario + '] Expected maxlength=10, got: ' + maxLength)
    }
    return
}

WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_state_container'), 3)
WebUI.click(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_state_container'))
WebUI.delay(1)
for (int i = 0; i < 10; i++) {
    int cnt = WebUI.executeJavaScript("return document.querySelectorAll('[id^=react-select-3-option]').length;", null)
    if (cnt > 0) break
    WebUI.click(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_state_container'))
    WebUI.delay(1)
}
WebUI.executeJavaScript("var o=document.querySelectorAll('[id^=react-select-3-option]'); for(var i=0;i<o.length;i++){if(o[i].innerText.trim()===arguments[0]){o[i].click();break;}}", Arrays.asList(State))
WebUI.delay(2)

WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_city_container'), 3)
WebUI.click(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_city_container'))
WebUI.delay(1)
for (int i = 0; i < 10; i++) {
    int cnt = WebUI.executeJavaScript("return document.querySelectorAll('[id^=react-select-4-option]').length;", null)
    if (cnt > 0) break
    WebUI.click(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_city_container'))
    WebUI.delay(1)
}
WebUI.executeJavaScript("var o=document.querySelectorAll('[id^=react-select-4-option]'); for(var i=0;i<o.length;i++){if(o[i].innerText.trim()===arguments[0]){o[i].click();break;}}", Arrays.asList(City))

WebUI.executeJavaScript("document.getElementById('submit').click();", null)
WebUI.delay(1)

String popup = WebUI.executeJavaScript(
    "return document.getElementById('example-modal-sizes-title-lg') ? document.getElementById('example-modal-sizes-title-lg').innerText.trim() : '';", null)
boolean formSubmitted = popup.equals('Thanks for submitting the form')
boolean shouldPass    = ExpectedResult.equals('PASS')

// TC008_InvalidMobileAlpha: tambahan assertion nilai aktual field Mobile
if (Scenario.startsWith('TC008')) {
    String actualMobile = WebUI.getAttribute(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_(10 Digits)_userNumber'), 'value')
    WebUI.takeScreenshot()
    WebUI.closeBrowser()
    if (!formSubmitted) {
        KeywordUtil.markPassed('[TC008_InvalidMobileAlpha] Form tidak tersubmit, nilai Mobile: "' + actualMobile + '" — PASSED')
    } else {
        KeywordUtil.markFailed('[TC008_InvalidMobileAlpha] Form tersubmit padahal Mobile berisi huruf — FAILED')
    }
    return
}

WebUI.takeScreenshot()

if (formSubmitted == shouldPass) {
    KeywordUtil.markPassed('[' + Scenario + '] Result matches expected: ' + ExpectedResult)
} else {
    KeywordUtil.markFailed('[' + Scenario + '] Expected ' + ExpectedResult + ' but form was ' + (formSubmitted ? 'submitted' : 'rejected'))
}

if (formSubmitted) {
    WebUI.executeJavaScript('document.body.style.zoom=\'60%\'', null)
    WebUI.delay(1)
    WebUI.executeJavaScript("var btn=document.getElementById('closeLargeModal'); if(btn) btn.click();", null)
    WebUI.executeJavaScript('document.body.style.zoom=\'100%\'', null)
}

WebUI.delay(3)
WebUI.closeBrowser()
