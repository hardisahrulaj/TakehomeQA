import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
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
WebUI.click(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/label_Male_custom-control-label'))
WebUI.setText(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_(10 Digits)_userNumber'), MobileNumber)

String maxLength = WebUI.getAttribute(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_(10 Digits)_userNumber'), 'maxlength')
if (maxLength == '10') {
	log.logPassed('Success : tidak bisa mengisi lebih dari 10 Digits')
	log.logPassed('Success : jumlah Maxlength = ' + maxLength)
	KeywordUtil.markPassed('Success : tidak bisa mengisi lebih dari 10 Digits')
	KeywordUtil.markPassed('Success : jumlah Maxlength = ' + maxLength)
	WebUI.takeScreenshot()
} else {
	log.logFailed('Failed : maxlength salah, jumlahnya: ' + maxLength)
	KeywordUtil.markFailed('Failed : Expected maxlength=10, got: ' + maxLength)
	WebUI.takeScreenshot()
}

WebUI.delay(3)
WebUI.closeBrowser()
