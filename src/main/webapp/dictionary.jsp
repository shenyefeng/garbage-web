<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:if test="${!empty cookie.lang}">
    <fmt:setLocale value="${cookie.lang.value}" />
</c:if>
<fmt:setBundle basename="i18n/messages" />
<script language="javascript">
    dictionary = {
    'title.login':'<fmt:message key="title.login"/>'
    ,'label.username':'<fmt:message key="label.username"/>'
    ,'label.weight':'<fmt:message key="label.weight"/>'
    ,'label.garbagesGrowth':'<fmt:message key="label.garbagesGrowth"/>'
    ,'title.error':'<fmt:message key="title.error"/>'
    ,'label.enterContents':'<fmt:message key="label.enterContents"/>'
    ,'label.qrCodeStart':'<fmt:message key="label.qrCodeStart"/>'
    ,'label.address':'<fmt:message key="label.address"/>'
    ,'label.giftGrantNum1':'<fmt:message key="label.giftGrantNum1"/>'
    ,'label.addGarbageTypes':'<fmt:message key="label.addGarbageTypes"/>'
    ,'QRCODE_00007':'<fmt:message key="QRCODE_00007"/>'
    ,'QRCODE_00006':'<fmt:message key="QRCODE_00006"/>'
    ,'label.female':'<fmt:message key="label.female"/>'
    ,'QRCODE_00005':'<fmt:message key="QRCODE_00005"/>'
    ,'QRCODE_00004':'<fmt:message key="QRCODE_00004"/>'
    ,'QRCODE_00003':'<fmt:message key="QRCODE_00003"/>'
    ,'label.birthday':'<fmt:message key="label.birthday"/>'
    ,'QRCODE_00002':'<fmt:message key="QRCODE_00002"/>'
    ,'QRCODE_00001':'<fmt:message key="QRCODE_00001"/>'
    ,'label.giftGrantNum':'<fmt:message key="label.giftGrantNum"/>'
    ,'label.qrCode':'<fmt:message key="label.qrCode"/>'
    ,'label.giftName':'<fmt:message key="label.giftName"/>'
    ,'label.addUser':'<fmt:message key="label.addUser"/>'
    ,'label.system.name':'<fmt:message key="label.system.name"/>'
    ,'label.ThreeMonth-t':'<fmt:message key="label.ThreeMonth-t"/>'
    ,'label.male':'<fmt:message key="label.male"/>'
    ,'label.addGarbageCans':'<fmt:message key="label.addGarbageCans"/>'
    ,'label.addGiftGrants':'<fmt:message key="label.addGiftGrants"/>'
    ,'title.index':'<fmt:message key="title.index"/>'
    ,'label.yes':'<fmt:message key="label.yes"/>'
    ,'label.deleteUsers':'<fmt:message key="label.deleteUsers"/>'
    ,'label.no':'<fmt:message key="label.no"/>'
    ,'label.Hour':'<fmt:message key="label.Hour"/>'
    ,'label.Year':'<fmt:message key="label.Year"/>'
    ,'label.gender':'<fmt:message key="label.gender"/>'
    ,'label.deleteGifts':'<fmt:message key="label.deleteGifts"/>'
    ,'label.SixMonth':'<fmt:message key="label.SixMonth"/>'
    ,'label.modifyGiftGrantsInfo':'<fmt:message key="label.modifyGiftGrantsInfo"/>'
    ,'label.modifyGarbageCansInfo':'<fmt:message key="label.modifyGarbageCansInfo"/>'
    ,'label.updatedBy':'<fmt:message key="label.updatedBy"/>'
    ,'label.scaleDoesnotOpen':'<fmt:message key="label.scaleDoesnotOpen"/>'
    ,'label.Alert2':'<fmt:message key="label.Alert2"/>'
    ,'label.Hour-t':'<fmt:message key="label.Hour-t"/>'
    ,'GIFT_GRANT_00002':'<fmt:message key="GIFT_GRANT_00002"/>'
    ,'GIFT_GRANT_00001':'<fmt:message key="GIFT_GRANT_00001"/>'
    ,'label.score':'<fmt:message key="label.score"/>'
    ,'label.addGarbages':'<fmt:message key="label.addGarbages"/>'
    ,'label.Year-t':'<fmt:message key="label.Year-t"/>'
    ,'label.deleteQrCodes':'<fmt:message key="label.deleteQrCodes"/>'
    ,'label.Week-t':'<fmt:message key="label.Week-t"/>'
    ,'label.ConfigName':'<fmt:message key="label.ConfigName"/>'
    ,'label.scoreWeight':'<fmt:message key="label.scoreWeight"/>'
    ,'label.RegisterUserGrowth':'<fmt:message key="label.RegisterUserGrowth"/>'
    ,'label.remberinfo':'<fmt:message key="label.remberinfo"/>'
    ,'label.Delete':'<fmt:message key="label.Delete"/>'
    ,'LOGIN_10008':'<fmt:message key="LOGIN_10008"/>'
    ,'LOGIN_10007':'<fmt:message key="LOGIN_10007"/>'
    ,'label.deleteGiftGrants':'<fmt:message key="label.deleteGiftGrants"/>'
    ,'label.qrCodeEnd':'<fmt:message key="label.qrCodeEnd"/>'
    ,'LOGIN_10006':'<fmt:message key="LOGIN_10006"/>'
    ,'LOGIN_10005':'<fmt:message key="LOGIN_10005"/>'
    ,'000':'<fmt:message key="000"/>'
    ,'LOGIN_10004':'<fmt:message key="LOGIN_10004"/>'
    ,'LOGIN_10003':'<fmt:message key="LOGIN_10003"/>'
    ,'LOGIN_10002':'<fmt:message key="LOGIN_10002"/>'
    ,'LOGIN_10001':'<fmt:message key="LOGIN_10001"/>'
    ,'label.updatedAt':'<fmt:message key="label.updatedAt"/>'
    ,'title.home':'<fmt:message key="title.home"/>'
    ,'label.modifyGarbagesInfo':'<fmt:message key="label.modifyGarbagesInfo"/>'
    ,'label.modifyGiftsInfo':'<fmt:message key="label.modifyGiftsInfo"/>'
    ,'label.garbageCount':'<fmt:message key="label.garbageCount"/>'
    ,'label.modifyQrCodesInfo':'<fmt:message key="label.modifyQrCodesInfo"/>'
    ,'label.Month':'<fmt:message key="label.Month"/>'
    ,'label.Day':'<fmt:message key="label.Day"/>'
    ,'label.deleteConfig':'<fmt:message key="label.deleteConfig"/>'
    ,'label.deleteGarbages':'<fmt:message key="label.deleteGarbages"/>'
    ,'label.clickreflash':'<fmt:message key="label.clickreflash"/>'
    ,'label.garbageWeight':'<fmt:message key="label.garbageWeight"/>'
    ,'label.addQrCode':'<fmt:message key="label.addQrCode"/>'
    ,'label.isAdmin':'<fmt:message key="label.isAdmin"/>'
    ,'label.modifyGarbageTypesInfo':'<fmt:message key="label.modifyGarbageTypesInfo"/>'
    ,'label.village':'<fmt:message key="label.village"/>'
    ,'label.canName':'<fmt:message key="label.canName"/>'
    ,'label.modifyUsersInfo':'<fmt:message key="label.modifyUsersInfo"/>'
    ,'label.Week':'<fmt:message key="label.Week"/>'
    ,'label.addConfig':'<fmt:message key="label.addConfig"/>'
    ,'label.createdBy':'<fmt:message key="label.createdBy"/>'
    ,'label.login':'<fmt:message key="label.login"/>'
    ,'label.modifyConfigInfo':'<fmt:message key="label.modifyConfigInfo"/>'
    ,'label.stationOp':'<fmt:message key="label.stationOp"/>'
    ,'label.phone':'<fmt:message key="label.phone"/>'
    ,'label.deleteGarbageTypes':'<fmt:message key="label.deleteGarbageTypes"/>'
    ,'0002':'<fmt:message key="0002"/>'
    ,'label.modify':'<fmt:message key="label.modify"/>'
    ,'0001':'<fmt:message key="0001"/>'
    ,'label.ThreeMonth':'<fmt:message key="label.ThreeMonth"/>'
    ,'label.search':'<fmt:message key="label.search"/>'
    ,'label.scaleDoesnotExist':'<fmt:message key="label.scaleDoesnotExist"/>'
    ,'label.realName':'<fmt:message key="label.realName"/>'
    ,'label.addGifts':'<fmt:message key="label.addGifts"/>'
    ,'label.webname':'<fmt:message key="label.webname"/>'
    ,'label.typeName':'<fmt:message key="label.typeName"/>'
    ,'label.Month-t':'<fmt:message key="label.Month-t"/>'
    ,'label.deleteGarbageCans':'<fmt:message key="label.deleteGarbageCans"/>'
    ,'label.SixMonth-t':'<fmt:message key="label.SixMonth-t"/>'
    ,'GARBAGE_ALL':'<fmt:message key="GARBAGE_ALL"/>'
    ,'label.Operating':'<fmt:message key="label.Operating"/>'
    ,'label.stationName':'<fmt:message key="label.stationName"/>'
    ,'label.ConfirmUpdate':'<fmt:message key="label.ConfirmUpdate"/>'
    ,'label.createdAt':'<fmt:message key="label.createdAt"/>'
    ,'label.password':'<fmt:message key="label.password"/>'
    ,'label.giftNum':'<fmt:message key="label.giftNum"/>'
    ,'label.deleteGarbageStations':'<fmt:message key="label.deleteGarbageStations"/>'
    ,'label.chkencrypt':'<fmt:message key="label.chkencrypt"/>'
    ,'label.addGarbageStations':'<fmt:message key="label.addGarbageStations"/>'
    ,'USER_ALL':'<fmt:message key="USER_ALL"/>'
    ,'label.validationcode':'<fmt:message key="label.validationcode"/>'
    ,'LOGOUT_10001':'<fmt:message key="LOGOUT_10001"/>'
    ,'label.ConfigValue':'<fmt:message key="label.ConfigValue"/>'
    ,'label.Day-t':'<fmt:message key="label.Day-t"/>'
    ,'label.logomsg':'<fmt:message key="label.logomsg"/>'
    ,'GIFT_00002':'<fmt:message key="GIFT_00002"/>'
    ,'label.selectContents':'<fmt:message key="label.selectContents"/>'
    ,'GIFT_00001':'<fmt:message key="GIFT_00001"/>'
    ,'label.modifyGarbageStationsInfo':'<fmt:message key="label.modifyGarbageStationsInfo"/>'
    ,'label.Confirm':'<fmt:message key="label.Confirm"/>'
    ,'label.remark':'<fmt:message key="label.remark"/>'
    ,'label.typeName':'<fmt:message key="label.typeName"/>'
    ,'label.sum':'<fmt:message key="label.sum"/>'
    ,'label.userId':'<fmt:message key="label.userId"/>'
    ,'label.userName':'<fmt:message key="label.userName"/>'
    ,'label.userPhone':'<fmt:message key="label.userPhone"/>'
    ,'label.user.score.desc':'<fmt:message key="label.user.score.desc"/>'
    ,'label.orderby':'<fmt:message key="label.orderby"/>'
};
</script>