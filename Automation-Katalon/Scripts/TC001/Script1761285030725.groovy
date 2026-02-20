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
WebUI.executeJavaScript("document.querySelector('label[for=gender-radio-1]').click();", null)
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

String popupText = WebUI.executeJavaScript("return document.getElementById('example-modal-sizes-title-lg') ? document.getElementById('example-modal-sizes-title-lg').innerText.trim() : '';", null)
if (popupText.equals('Thanks for submitting the form')) {
	log.logPassed('Success : Data Form berhasil terkirim dan tersimpan')
	log.logPassed('Success : Popup "Thanks for submitting the form" muncul')
	KeywordUtil.markPassed('Success : Data Form berhasil terkirim dan tersimpan')
	KeywordUtil.markPassed('Success : Popup "Thanks for submitting the form" muncul')

	String popupBody = WebUI.executeJavaScript(
		"var rows=document.querySelectorAll('.table-responsive tbody tr'); var result={}; rows.forEach(function(r){var cells=r.querySelectorAll('td'); if(cells.length===2) result[cells[0].innerText.trim()]=cells[1].innerText.trim();}); return JSON.stringify(result);",
		null)
	def data = new groovy.json.JsonSlurper().parseText(popupBody)

	String expectedName = FirstName + ' ' + LastName
	String actualName   = data['Student Name'] ?: ''
	if (actualName.equals(expectedName)) {
		log.logPassed('Assert Student Name PASSED: ' + actualName)
	} else {
		log.logFailed('Assert Student Name FAILED – expected: ' + expectedName + ', actual: ' + actualName)
	}

	String actualEmail = data['Student Email'] ?: ''
	if (actualEmail.equals(StudentEmail)) {
		log.logPassed('Assert Student Email PASSED: ' + actualEmail)
	} else {
		log.logFailed('Assert Student Email FAILED – expected: ' + StudentEmail + ', actual: ' + actualEmail)
	}

	String actualGender = data['Gender'] ?: ''
	if (actualGender.equals('Male')) {
		log.logPassed('Assert Gender PASSED: ' + actualGender)
	} else {
		log.logFailed('Assert Gender FAILED – expected: Male, actual: ' + actualGender)
	}

	String actualMobile = data['Mobile'] ?: ''
	if (actualMobile.equals(MobileNumber)) {
		log.logPassed('Assert Mobile PASSED: ' + actualMobile)
	} else {
		log.logFailed('Assert Mobile FAILED – expected: ' + MobileNumber + ', actual: ' + actualMobile)
	}

	String actualAddress = data['Address'] ?: ''
	if (actualAddress.equals(CurrentAddress)) {
		log.logPassed('Assert Address PASSED: ' + actualAddress)
	} else {
		log.logFailed('Assert Address FAILED – expected: ' + CurrentAddress + ', actual: ' + actualAddress)
	}

	String expectedStateCity = state + ' ' + city
	String actualStateCity   = data['State and City'] ?: ''
	if (actualStateCity.equals(expectedStateCity)) {
		log.logPassed('Assert State and City PASSED: ' + actualStateCity)
	} else {
		log.logFailed('Assert State and City FAILED – expected: ' + expectedStateCity + ', actual: ' + actualStateCity)
	}

	WebUI.takeScreenshot()
} else {
	log.logFailed('Failed : Gagal mengirim data atau popup tidak muncul')
	KeywordUtil.markFailed('Failed : Gagal mengirim data atau popup tidak muncul')
	WebUI.takeScreenshot()
}

WebUI.executeJavaScript('document.body.style.zoom=\'60%\'', null)
WebUI.delay(1)
WebUI.executeJavaScript("var btn=document.getElementById('closeLargeModal'); if(btn) btn.click();", null)
WebUI.executeJavaScript('document.body.style.zoom=\'100%\'', null)
WebUI.delay(3)
WebUI.closeBrowser()
