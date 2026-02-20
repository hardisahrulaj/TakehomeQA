import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.util.KeywordUtil

// TC009_EmptyAllFields
// Akses form langsung, tidak isi field apapun, klik Submit
// Ekspektasi: form TIDAK tersubmit, modal konfirmasi tidak muncul

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
WebUI.scrollToElement(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Name_firstName'), 1)

// Tidak mengisi field apapun — langsung scroll ke bawah dan klik Submit
WebUI.executeJavaScript('window.scrollBy(0, 300)', null)
WebUI.executeJavaScript("var ads=document.querySelectorAll('.ad-container,.close-button,#adplus-anchor,[id*=google_ads],[class*=adsbygoogle]'); ads.forEach(function(a){a.remove();});", null)
WebUI.executeJavaScript("document.getElementById('submit').click();", null)

// Assertion: modal tidak boleh muncul
String popupText = WebUI.executeJavaScript("return document.getElementById('example-modal-sizes-title-lg') ? document.getElementById('example-modal-sizes-title-lg').innerText.trim() : '';", null)
boolean modalMuncul = popupText.equals('Thanks for submitting the form')

// Verifikasi field First Name masih kosong (form tidak pindah halaman)
String firstNameValue = WebUI.getAttribute(findTestObject('Object Repository/Take Home Test/Page_DEMOQA/input_Name_firstName'), 'value')

WebUI.takeScreenshot()

if (!modalMuncul) {
    log.logPassed('Success : Modal tidak muncul — form tidak tersubmit saat semua field kosong')
    log.logPassed('Success : Field First Name kosong: "' + firstNameValue + '"')
    KeywordUtil.markPassed('TC009_EmptyAllFields : Form tidak tersubmit saat semua field kosong — PASSED')
} else {
    log.logFailed('Failed : Modal muncul padahal semua field kosong — form seharusnya tidak tersubmit')
    KeywordUtil.markFailed('TC009_EmptyAllFields : Form tersubmit padahal semua field kosong — FAILED')
}

WebUI.closeBrowser()
