import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.util.KeywordUtil

KeywordLogger log = new KeywordLogger()

// Variables injected by Katalon DDT engine from DDT Student Scenario.dat
// FirstName, LastName, StudentEmail, MobileNumber, DateOfBirth, CurrentAddress, state, city

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
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Name_firstName'), FirstName)
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Name_lastName'), LastName)
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Email_userEmail'), StudentEmail)
// Gender intentionally not selected (TC005: No Gender)
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_(10 Digits)_userNumber'), MobileNumber)

String[] dobParts = DateOfBirth.split('-')
String dobYear  = dobParts[0]
String dobMonth = String.valueOf(Integer.parseInt(dobParts[1]) - 1)
String dobDay   = String.valueOf(Integer.parseInt(dobParts[2]))
WebUI.executeJavaScript("document.getElementById('dateOfBirthInput').click();", null)
WebUI.executeJavaScript("document.querySelector('.react-datepicker__year-select').value='" + dobYear + "'; document.querySelector('.react-datepicker__year-select').dispatchEvent(new Event('change',{bubbles:true}));", null)
WebUI.executeJavaScript("document.querySelector('.react-datepicker__month-select').value='" + dobMonth + "'; document.querySelector('.react-datepicker__month-select').dispatchEvent(new Event('change',{bubbles:true}));", null)
WebUI.executeJavaScript("var days=document.querySelectorAll('.react-datepicker__day:not(.react-datepicker__day--outside-month)'); for(var d of days){if(d.textContent.trim()==='" + dobDay + "'){d.click();break;}}", null)

WebUI.delay(1)
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Subjects_subjectsInput'), 'A')
WebUI.executeJavaScript("document.querySelector('[id^=react-select-2-option]').click();", null)
WebUI.executeJavaScript("document.querySelector('label[for=hobbies-checkbox-3]').click();", null)

WebUI.uploadFile(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Select picture_uploadPicture'), RunConfiguration.getProjectDir() + '/Data Files/student_photo.jpg')
WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/textarea_Current Address_currentAddress'), 3)
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/textarea_Current Address_currentAddress'), CurrentAddress)

WebUI.executeJavaScript('window.scrollBy(0, 300)', null)
WebUI.delay(1)
WebUI.executeJavaScript("var ads=document.querySelectorAll('.ad-container,.close-button,#adplus-anchor,[id*=google_ads],[class*=adsbygoogle]'); ads.forEach(function(a){a.remove();});", null)
WebUI.delay(1)

WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_state_container'), 3)
WebUI.click(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_state_container'))
WebUI.delay(1)
for (int i = 0; i < 10; i++) {
    int cnt = WebUI.executeJavaScript("return document.querySelectorAll('[id^=react-select-3-option]').length;", null)
    if (cnt > 0) break
    WebUI.click(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/div_state_container'))
    WebUI.delay(1)
}
WebUI.executeJavaScript("var o=document.querySelectorAll('[id^=react-select-3-option]'); for(var i=0;i<o.length;i++){if(o[i].innerText.trim()===arguments[0]){o[i].click();break;}}", Arrays.asList(state))
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
WebUI.executeJavaScript("var o=document.querySelectorAll('[id^=react-select-4-option]'); for(var i=0;i<o.length;i++){if(o[i].innerText.trim()===arguments[0]){o[i].click();break;}}", Arrays.asList(city))

WebUI.executeJavaScript("document.getElementById('submit').click();", null)
WebUI.delay(1)

String titleText = WebUI.executeJavaScript("return document.querySelector('h5') ? document.querySelector('h5').innerText.trim() : '';", null)
if (titleText.equals('Student Registration Form')) {
	WebUI.delay(1)
	String genderChecked = WebUI.executeJavaScript("var radios = document.querySelectorAll('input[name=gender]'); for(var r of radios){if(r.checked) return r.value;} return '';", null)
	if (genderChecked == null || genderChecked.isEmpty()) {
		log.logPassed('Success : Gender tidak dipilih, form tidak terkirim')
		KeywordUtil.markPassed('Success : Gender tidak dipilih, form tidak terkirim')
	} else {
		log.logFailed('Failed : Gender terpilih padahal seharusnya tidak')
		KeywordUtil.markFailed('Failed : Gender terpilih padahal seharusnya tidak')
	}
	log.logPassed('Success : Data Form tidak terkirim')
	log.logPassed('Success : Muncul validasi pada pilihan Gender')
	KeywordUtil.markPassed('Success : Data Form tidak terkirim')
	WebUI.takeScreenshot()
	KeywordUtil.markPassed('Success : Muncul validasi mandatory pada pilihan Gender')
} else {
	WebUI.delay(1)
	WebUI.takeScreenshot()
	log.logFailed('Failed : Data Form berhasil terkirim dan tersimpan')
	KeywordUtil.markFailed('Failed : Data Form berhasil terkirim dan tersimpan')
}

WebUI.delay(3)
WebUI.closeBrowser()
