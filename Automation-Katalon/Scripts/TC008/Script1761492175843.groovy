import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.util.KeywordUtil

// TC008_InvalidMobileAlpha
// Semua field mandatory diisi valid, kecuali Mobile diisi huruf (ABCDEF1234)
// Ekspektasi: form TIDAK tersubmit, modal konfirmasi tidak muncul
// Variables: FirstName, LastName, StudentEmail, MobileNumber, DateOfBirth, CurrentAddress, state, city

KeywordLogger log = new KeywordLogger()

WebUI.openBrowser('')
WebUI.navigateToUrl('https://demoqa.com/')
WebUI.maximizeWindow()
WebUI.waitForPageLoad(10)
WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/card_elements'), 5)
WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(WebUI.findWebElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/card_elements'))))
WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(WebUI.findWebElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/menu_forms'))))
WebUI.delay(1)
WebUI.executeJavaScript('arguments[0].click();', Arrays.asList(WebUI.findWebElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/menu_practiceForm'))))
WebUI.waitForPageLoad(5)
WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/label_Male_custom-control-label'), 1)

WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Name_firstName'), FirstName)
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Name_lastName'), LastName)
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Email_userEmail'), StudentEmail)
WebUI.executeJavaScript("document.querySelector('label[for=gender-radio-1]').click();", null)

// Isi Mobile dengan huruf — field hanya menerima angka, karakter huruf akan diabaikan browser
// sehingga field tetap kosong/tidak valid → form tidak bisa submit
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_(10 Digits)_userNumber'), MobileNumber)

String[] dobParts = DateOfBirth.split('-')
WebUI.executeJavaScript("document.getElementById('dateOfBirthInput').click();", null)
WebUI.executeJavaScript("document.querySelector('.react-datepicker__year-select').value='" + dobParts[0] + "'; document.querySelector('.react-datepicker__year-select').dispatchEvent(new Event('change',{bubbles:true}));", null)
WebUI.executeJavaScript("document.querySelector('.react-datepicker__month-select').value='" + String.valueOf(Integer.parseInt(dobParts[1]) - 1) + "'; document.querySelector('.react-datepicker__month-select').dispatchEvent(new Event('change',{bubbles:true}));", null)
WebUI.executeJavaScript("var days=document.querySelectorAll('.react-datepicker__day:not(.react-datepicker__day--outside-month)'); for(var d of days){if(d.textContent.trim()==='" + String.valueOf(Integer.parseInt(dobParts[2])) + "'){d.click();break;}}", null)

WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Subjects_subjectsInput'), 'A')
WebUI.executeJavaScript("document.querySelector('[id^=react-select-2-option]').click();", null)
WebUI.executeJavaScript("document.querySelector('label[for=hobbies-checkbox-3]').click();", null)

WebUI.uploadFile(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Select picture_uploadPicture'), RunConfiguration.getProjectDir() + '/Data Files/student_photo.jpg')
WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/textarea_Current Address_currentAddress'), 5)
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/textarea_Current Address_currentAddress'), CurrentAddress)

WebUI.executeJavaScript('window.scrollBy(0, 300)', null)
WebUI.executeJavaScript("var ads=document.querySelectorAll('.ad-container,.close-button,#adplus-anchor,[id*=google_ads],[class*=adsbygoogle]'); ads.forEach(function(a){a.remove();});", null)

WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_state_container'), 5)
WebUI.click(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_state_container'))
WebUI.waitForElementPresent(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_state_container'), 5)
for (int i = 0; i < 10; i++) {
    int cnt = WebUI.executeJavaScript("return document.querySelectorAll('[id^=react-select-3-option]').length;", null)
    if (cnt > 0) break
    WebUI.click(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_state_container'))
}
WebUI.executeJavaScript("var o=document.querySelectorAll('[id^=react-select-3-option]'); for(var i=0;i<o.length;i++){if(o[i].innerText.trim()===arguments[0]){o[i].click();break;}}", Arrays.asList(state))

WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_city_container'), 5)
WebUI.click(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_city_container'))
WebUI.waitForElementPresent(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_city_container'), 5)
for (int i = 0; i < 10; i++) {
    int cnt = WebUI.executeJavaScript("return document.querySelectorAll('[id^=react-select-4-option]').length;", null)
    if (cnt > 0) break
    WebUI.click(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_city_container'))
}
WebUI.executeJavaScript("var o=document.querySelectorAll('[id^=react-select-4-option]'); for(var i=0;i<o.length;i++){if(o[i].innerText.trim()===arguments[0]){o[i].click();break;}}", Arrays.asList(city))

WebUI.executeJavaScript("document.getElementById('submit').click();", null)

// Assertion: verifikasi nilai aktual field Mobile setelah submit
String actualMobile = WebUI.getAttribute(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_(10 Digits)_userNumber'), 'value')
String popupText = WebUI.executeJavaScript("return document.getElementById('example-modal-sizes-title-lg') ? document.getElementById('example-modal-sizes-title-lg').innerText.trim() : '';", null)
boolean modalMuncul = popupText.equals('Thanks for submitting the form')

WebUI.takeScreenshot()

if (!modalMuncul) {
    log.logPassed('Success : Modal tidak muncul — form tidak tersubmit dengan Mobile berisi huruf')
    log.logPassed('Success : Nilai aktual Mobile field: "' + actualMobile + '" (huruf diabaikan oleh browser)')
    KeywordUtil.markPassed('TC008_InvalidMobileAlpha : Form tidak tersubmit karena Mobile berisi huruf — PASSED')
} else {
    log.logFailed('Failed : Modal muncul padahal Mobile berisi huruf — form seharusnya tidak tersubmit')
    KeywordUtil.markFailed('TC008_InvalidMobileAlpha : Form tersubmit padahal Mobile berisi huruf — FAILED')
}

WebUI.closeBrowser()
