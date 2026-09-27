package com.tawajood.the_community_user.app.language

interface Strings {
    // ********** GENERIC **********
    val appName: String
    val continueButton: String
    val save: String
    val requiredField: String
    val pleaseLogInFirst: String
    val welcome: String
    val tryAgain: String
    val noInternet: String
    val back: String
    val checkFromPhone: String
    val second: String
    val whatIsYourName: String
    val registerDes: String
    val firstName: String
    val lastName: String
    val email: String

    // ********** ON BOARDING **********
    val onBoardingTitle1: String
    val onBoardingTitle2: String
    val onBoardingTitle3: String
    val onBoardingContent1: String
    val onBoardingContent2: String
    val onBoardingContent3: String
    val startNow: String

    // ********** LOGIN **********
    val login: String
    val welcomeBack: String
    val pleaseEnterYourDataToLogin: String
    val phoneNumber: String
    val password: String
    val forgetPasswordQuestion: String
    val didNotHaveAccountQuestion: String
    val createAccount: String
    val pleaseEnterValidPhoneNumber: String
    val pleaseEnterValidPassword: String
    val loginAsAGuest: String

    // ********** FORGET PASSWORD **********
    val forgetPassword: String
    val confirmationByPhoneNumber: String
    val pleaseEnterYourPhoneNumberToProceedWithChangingYourPassword: String
    val passwordConfirmation: String

    // ********** OTP **********
    val phoneVerification: String
    val otpDescription: String
    val didNotReceiveTheCodeQuestion: String
    val resendIt: String
    val enter4Digits: String

    // ********** RESET PASSWORD **********
    val newPassword: String
    val createNewPassword: String
    val createNewPasswordForYou: String
    val createPassword: String
    val confirmPassword: String
    val pleaseEnterValidPasswordConfirmation: String

    // ********** SIGNUP **********
    val name: String
    val byRegisteringYouAgreeTo: String
    val termsAndConditions: String
    val haveAnAccountQuestion: String
    val termsContent: String
    val iAgreeToTermsAndConditions: String

    // ********** Bottom Navigation **********
    val home: String
    val topRate: String
    val matches: String
    val record: String
    val profile: String

    // ********** Home **********
    val point: String
    val points: String
    val predictTheChampion: String
    val championships: String
    val goldenCard: String
    val goldenCardDescription: String
    val applicationGuide: String
    val yourPredict: String
    val predict: String
    val predictResult: String
    val now: String
    val finished: String
    val seeMore: String
    val todayMatches: String
    val result: String
    val tody: String
    val yesterday: String
    val notMatchesToday: String

    // ********** Notifications **********
    val notifications: String
    val noNotifications: String
    val predictAndScoreDes: String

    // ********** Golden Card **********
    val useCard: String
    val useCard_2: String
    val used: String
    val theGoldenCardWasUsedInTheMatch: String
    val notAvailableInThisMatch: String
    val cancel: String

    // ********** Golden Card **********
    val yourComprehensiveGuide: String
    val competitionPolicy: String
    val howGameSenseWork: String

    // ********** Predict The Champion **********
    val predictWorldCupChampion: String
    val howWillWinWorldCupChampionQuestion: String
    val send: String
    val worldCupChampion: String

    // ********** Match Details **********
    val howWillWinQuestion: String
    val draw: String
    val resultOfDraw: String
    val winnerOnPenalties: String
    val original: String
    val extra: String
    val drawResult: String
    val yourPredictionHasBeenSubmitted: String
    val yourPredictionHasBeenSuccessfullySubmitted: String
    val returnToTheHomeScreen: String
    val penaltiesResult: String

    // ********** Predictions Record **********
    val predictionsRecord: String
    val all: String

    // ********** Settings **********
    val myRank: String
    val totalPoints: String
    val rank: String
    val of: String
    val player: String
    val personalData: String
    val personProfile: String
    val predictionsHistory: String
    val settingsAndPrivacy: String
    val contactUs: String
    val settings: String
    val logout: String

    // ********** Profile **********
    val editProfile: String
    val deleteAccount: String
    val changePassword: String

    // ********** Edit Profile **********
    val changeImage: String
    val doYouWantToDeleteYourAccountQuestion: String
    val deleteAccountDes: String
    val yesDelete: String
    val noThanks: String

    // ********** App Settings **********
    val language: String
    val arabic: String
    val english: String

    // ********** Change Password **********
    val changeYourPassword: String
    val oldPassword: String

    // ********** COUNTRY PICKER **********
    val selectCountry: String
    val searchCountry: String
    val searchByNameOrCode: String

    // EMPTY
    val noTodayMatches: String
    val noMatches: String
    val noMatchesDescription: String
    val chooseDay: String
    val followUs: String
    val followUs2: String
    val upcomingMatches: String
    val returnToHomePage: String
    fun winOnPenalties(teamName: String): String
    fun collectedPoints(points: Int): String
    val predictToCollect: String
    val newVersionAvailable: String
    val updateNow: String
    val newUpdate: String

    // ********** CHAMPIONSHIP **********
    fun predictChampionTitle(championshipName: String): String
    fun whoWillWinTitle(championshipName: String): String
    fun championOfTitle(championshipName: String): String

    // ********** Championships Screen **********
    val championshipsTitle: String
    val subscribers: String
    val activeNow: String
    val upcoming: String
    val finishedStatus: String
    val pleaseEnterValidEmail: String
    val inviteCode: String

    // ********** Terms & Conditions **********
    val termsAndConditionsHeader: String
    val termsAndConditionsBody: String
    val agreeToTermsAndPrivacy: String

    // ********** Account Type **********
    val chooseAccountType: String
    fun helloUser(name: String): String
    val pleaseChooseAccountType: String
    val independentDriver: String
    val independentDriverDesc: String
    val companyOrFleet: String
    val companyOrFleetDesc: String

    // ********** Profile Completion **********
    val account: String
    val documents: String
    val startBtn: String
    val personalPhoto: String
    val idCardFrontBack: String
    val drivingLicenseFrontBack: String
    val insuranceCertificate: String
    val criminalRecord: String
    val transportationMeans: String
    val companyDetails: String
    val taxInformation: String
    val agreeToTerms: String
    val rejectedStatus: String
    val completedStatus: String
    val notUploadedStatus: String
    val underReviewStatus: String
    val helpBtn: String

    // ********** Personal Photo **********
    val personalPhotoTitle: String
    val capturePersonalPhotoHeader: String
    val retakePhotoWarning: String
    val personalPhotoRule1: String
    val personalPhotoRule2: String
    val personalPhotoRule3: String
    val capturePhotoBtn: String
    val personalPhotoSuccessTitle: String
    val personalPhotoSuccessSubtitle: String
    val doneOkBtn: String

    // ********** Driving License **********
    val drivingLicenseTitle: String
    val captureDrivingLicenseHeader: String
    val drivingLicenseWarning: String
    val drivingLicenseExpDateLabel: String
    val selectDatePlaceholder: String
    val drivingLicenseFrontLabel: String
    val uploadPlaceholder: String
    val drivingLicenseBackLabel: String
    val drivingLicenseSuccessTitle: String
    val drivingLicenseSuccessSubtitle: String

    // ********** Identity Card **********
    val identityCardTitle: String
    val captureIdentityCardHeader: String
    val identityCardExpDateLabel: String
    val identityCardFrontLabel: String
    val identityCardBackLabel: String
    val identityCardSuccessTitle: String
    val identityCardSuccessSubtitle: String

    // ********** Vehicle Information **********
    val vehicleInfoTitle: String
    val vehicleInfoHeader: String
    val vehicleManufacturerLabel: String
    val vehicleModelLabel: String
    val vehicleYearLabel: String
    val vehicleLicensePlateLabel: String

    // ********** Company Information & Success States **********
    val companyInfoTitle: String
    val companyNameLabel: String
    val companyOwnerLabel: String
    val commercialRegNumLabel: String
    val licenseRegNumLabel: String
    val saveBtn: String
    val submittedSuccessTitle: String
    val companyInfoSuccessMessage: String
    val vehicleInfoSuccessMessage: String

    // ********** Joining Contract **********
    val joiningContractTitle: String
    val yourSignatureLabel: String
    val pleaseSignLabel: String
    val clearSignatureBtn: String
    val agreeTermsConditionsPrivacyPolicy: String
}