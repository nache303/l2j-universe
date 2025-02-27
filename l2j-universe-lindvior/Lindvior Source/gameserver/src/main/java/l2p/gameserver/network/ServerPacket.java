/*
 * Copyright Mazaffaka Project (c) 2013.
 */

/*
 * Copyright Murzik Dev Team (c) 2013.
 */

package l2p.gameserver.network;

public enum ServerPacket {
    Die(0x00, 0),
    Revive(0x01, 0),
    AttackOutOfRange(0x02, 0),
    AttackinCoolTime(0x03, 0),
    AttackDeadTarget(0x04, 0),
    SpawnItem(0x05, 0),
    SellList(0x06, 0),
    BuyList(0x07, 0),
    DeleteObject(0x08, 0),
    CharacterSelectionInfo(0x09, 0),
    LoginFail(0x0a, 0),
    CharacterSelected(0x0B, 0),
    NpcInfo(0x0C, 0),
    NewCharacterSuccess(0x0d, 0),
    NewCharacterFail(0x10, 0),
    CharacterCreateSuccess(0x1d, 0),
    CharacterCreateFail(0x10, 0),
    ItemList(0x11, 0),
    SunRise(0x12, 0),
    SunSet(0x13, 0),
    TradeStart(0x14, 0),
    TradeStartOk(21, 0),
    DropItem(22, 0),
    GetItem(23, 0),
    StatusUpdate(24, 0),
    NpcHtmlMessage(25, 0),
    TradeOwnAdd(26, 0),
    TradeOtherAdd(27, 0),
    TradeDone(0x1c, 0),   // унас SendTradeDone
    CharacterDeleteSuccess(29, 0),
    CharacterDeleteFail(30, 0),
    ActionFail(31, 0),
    SeverClose(32, 0),
    InventoryUpdate(33, 0),
    TeleportToLocation(34, 0),
    TargetSelected(35, 0),
    TargetUnselected(36, 0),
    AutoAttackStart(37, 0),
    AutoAttackStop(38, 0),
    SocialAction(39, 0),
    ChangeMoveType(40, 0),
    ChangeWaitType(41, 0),
    ManagePledgePower(42, 0),
    CreatePledge(43, 0),
    AskJoinPledge(44, 0),
    JoinPledge(45, 0),
    VersionCheck(0x2e, 0),         // у нас KeyPacket
    MTL(47, 0),
    NS(48, 0),
    CI(49, 0),
    UI(50, 0),
    Attack(51, 0),
    WithdrawalPledge(52, 0),
    OustPledgeMember(53, 0),
    SetOustPledgeMember(54, 0),
    DismissPledge(55, 0),
    SetDismissPledge(56, 0),
    AskJoinParty(57, 0),
    JoinParty(58, 0),
    WithdrawalParty(59, 0),
    OustPartyMember(60, 0),
    SetOustPartyMember(61, 0),
    DismissParty(62, 0),
    SetDismissParty(63, 0),
    MagicAndSkillList(64, 0),
    WareHouseDepositList(65, 0),
    WareHouseWithdrawList(66, 0),
    WareHouseDone(67, 0),
    ShortCutRegister(68, 0),
    ShortCutInit(69, 0),
    ShortCutDelete(70, 0),
    StopMove(71, 0),
    MagicSkillUse(72, 0),
    MagicSkillCanceled(73, 0),
    Say2(74, 0),
    EquipUpdate(75, 0),
    DoorInfo(76, 0),
    DoorStatusUpdate(77, 0),
    PartySmallWindowAll(78, 0),
    PartySmallWindowAdd(79, 0),
    PartySmallWindowDeleteAll(80, 0),
    PartySmallWindowDelete(81, 0),
    PartySmallWindowUpdate(82, 0),
    TradePressOwnOk(83, 0),
    MagicSkillLaunched(84, 0),
    FriendAddRequestResult(85, 0),
    FriendAdd(86, 0),
    FriendRemove(87, 0),
    FriendList(88, 0),
    FriendStatus(89, 0),
    PledgeShowMemberListAll(90, 0),
    PledgeShowMemberListUpdate(91, 0),
    PledgeShowMemberListAdd(92, 0),
    PledgeShowMemberListDelete(93, 0),
    MagicList(94, 0),
    SkillList(95, 0),
    VehicleInfo(96, 0),
    FinishRotating(97, 0),
    SystemMessage(98, 0),
    StartPledgeWar(99, 0),
    ReplyStartPledgeWar(100, 0),
    StopPledgeWar(101, 0),
    ReplyStopPledgeWar(102, 0),
    SurrenderPledgeWar(103, 0),
    ReplySurrenderPledgeWar(104, 0),
    SetPledgeCrest(105, 0),
    PledgeCrest(106, 0),
    SetupGauge(107, 0),
    VehicleDeparture(108, 0),
    VehicleCheckLocation(109, 0),
    GetOnVehicle(110, 0),
    GetOffVehicle(111, 0),
    TradeRequest(0x70, 0),   // у нас SendTradeRequest
    RestartResponse(113, 0),
    MoveToPawn(114, 0),
    SSQInfo(115, 0),
    GameGuardQuery(116, 0),
    L2FriendList(117, 0),
    L2Friend(118, 0),
    L2FriendStatus(119, 0),
    L2FriendSay(120, 0),
    ValidateLocation(121, 0),
    StartRotating(122, 0),
    ShowBoard(123, 0),
    ChooseInventoryItem(124, 0),
    Dummy1(125, 0),
    MoveToLocationInVehicle(126, 0),
    StopMoveInVehicle(127, 0),
    ValidateLocationInVehicle(128, 0),
    TradeUpdate(129, 0),
    TradePressOtherOk(130, 0),
    FriendAddRequest(131, 0),
    LogOutOk(0x84, 0),      // Унас LeaveWorld
    AbnormalStatusUpdate(133, 0),
    QuestList(134, 0),
    EnchantResult(135, 0),
    PledgeShowMemberListDeleteAll(136, 0),
    PledgeInfo(137, 0),
    PledgeExtendedInfo(138, 0),
    SurrenderPersonally(139, 0),
    Ride(140, 0),
    Dummy2(141, 0),
    PledgeShowInfoUpdate(142, 0),
    ClientAction(143, 0),
    AcquireSkillList(144, 0),
    AcquireSkillInfo(145, 0),
    ServerObjectInfo(146, 0),
    GMHide(147, 0),
    AcquireSkillDone(148, 0),
    GMViewCharacterInfo(149, 0),
    GMViewPledgeInfo(150, 0),
    GMViewSkillInfo(151, 0),
    GMViewMagicInfo(152, 0),
    GMViewQuestInfo(153, 0),
    GMViewItemList(154, 0),
    GMViewWarehouseWithdrawList(155, 0),
    ListPartyWating(156, 0),
    PartyRoomInfo(157, 0),
    PlaySound(158, 0),
    StaticObject(159, 0),
    PrivateStoreManageList(160, 0),
    PrivateStoreList(161, 0),
    PrivateStoreMsg(162, 0),
    ShowMinimap(163, 0),
    ReviveRequest(164, 0),
    AbnormalVisualEffect(165, 0),
    TutorialShowHtml(166, 0),
    TutorialShowQuestionMark(167, 0),
    TutorialEnableClientEvent(168, 0),
    TutorialCloseHtml(169, 0),
    ShowRadar(170, 0),
    WithdrawAlliance(171, 0),
    OustAllianceMemberPledge(172, 0),
    DismissAlliance(173, 0),
    SetAllianceCrest(174, 0),
    AllianceCrest(175, 0),
    ServerCloseSocket(176, 0),
    PetStatusShow(177, 0),
    PetInfo(178, 0),
    PetItemList(179, 0),
    PetInventoryUpdate(180, 0),
    AllianceInfo(181, 0),
    PetStatusUpdate(182, 0),
    PetDelete(183, 0),
    DeleteRadar(184, 0),
    MyTargetSelected(185, 0),
    PartyMemberPosition(186, 0),
    AskJoinAlliance(187, 0),
    JoinAlliance(188, 0),
    PrivateStoreBuyManageList(189, 0),
    PrivateStoreBuyList(190, 0),
    PrivateStoreBuyMsg(191, 0),
    VehicleStart(192, 0),
    RequestTimeCheck(193, 0),
    StartAllianceWar(194, 0),
    ReplyStartAllianceWar(195, 0),
    StopAllianceWar(196, 0),
    ReplyStopAllianceWar(197, 0),
    SurrenderAllianceWar(198, 0),
    SkillCoolTime(199, 0),
    PackageToList(200, 0),
    CastleSiegeInfo(0xC9, 0),
    CastleSiegeAttackerList(202, 0),
    CastleSiegeDefenderList(203, 0),
    NickNameChanged(204, 0),
    PledgeStatusChanged(205, 0),
    RelationChanged(206, 0),
    EventTrigger(207, 0),
    MultiSellList(208, 0),
    SetSummonRemainTime(209, 0),
    PackageSendableList(210, 0),
    EarthQuake(211, 0),
    FlyToLocation(212, 0),
    BlockList(213, 0),
    SpecialCamera(214, 0),
    NormalCamera(215, 0),
    SkillRemainSec(216, 0),
    NetPing(217, 0),
    Dice(218, 0),
    Snoop(219, 0),
    RecipeBookItemList(220, 0),
    RecipeItemMakeInfo(221, 0),
    RecipeShopManageList(222, 0),
    RecipeShopSellList(223, 0),
    RecipeShopItemInfo(224, 0),
    RecipeShopMsg(225, 0),
    ShowCalc(226, 0),
    MonRaceInfo(227, 0),
    HennaItemInfo(228, 0),
    HennaInfo(229, 0),
    HennaUnequipList(230, 0),
    HennaUnequipInfo(231, 0),
    MacroList(232, 0),
    BuyListSeed(233, 0),
    ShowTownMap(234, 0),
    ObserverStart(235, 0),
    ObserverEnd(236, 0),
    ChairSit(237, 0),
    HennaEquipList(238, 0),
    SellListProcure(239, 0),
    GMHennaInfo(240, 0),
    RadarControl(241, 0),
    ClientSetTime(242, 0),
    ConfirmDlg(243, 0),
    PartySpelled(244, 0),
    ShopPreviewList(245, 0),
    ShopPreviewInfo(246, 0),
    CameraMode(247, 0),
    ShowXMasSeal(248, 0),
    EtcStatusUpdate(249, 0),
    ShortBuffStatusUpdate(250, 0),
    SSQStatus(251, 0),
    PetitionVote(252, 0),
    AgitDecoInfo(253, 0),
    Dummy(0xFE, 0),
    ExDummy1(0xFE, 1),
    ExRegenMax(0xFE, 2),
    ExEventMatchUserInfo(0xFE, 3),
    ExColosseumFenceInfo(0xFE, 4),
    ExEventMatchSpelledInfo(0xFE, 5),
    ExEventMatchFirecracker(0xFE, 6),
    ExEventMatchTeamUnlocked(0xFE, 7),
    ExEventMatchGMTest(0xFE, 8),
    ExPartyRoomMember(0xFE, 9),
    ExClosePartyRoom(0xFE, 10),
    ExManagePartyRoomMember(0xFE, 11),
    ExEventMatchLockResult(0xFE, 12),
    ExAutoSoulShot(0xFE, 13),
    ExEventMatchList(0xFE, 14),
    ExEventMatchObserver(0xFE, 15),
    ExEventMatchMessage(0xFE, 16),
    ExEventMatchScore(0xFE, 17),
    ExServerPrimitive(0xFE, 18),
    ExOpenMPCC(0xFE, 19),
    ExCloseMPCC(0xFE, 20),
    ExShowCastleInfo(0xFE, 21),
    ExShowFortressInfo(0xFE, 22),
    ExShowAgitInfo(0xFE, 23),
    ExShowFortressSiegeInfo(0xFE, 24),
    ExPartyPetWindowAdd(0xFE, 25),
    ExPartyPetWindowUpdate(0xFE, 26),
    ExAskJoinMPCC(0xFE, 27),
    ExPledgeEmblem(0xFE, 28),
    ExEventMatchTeamInfo(0xFE, 29),
    ExEventMatchCreate(0xFE, 30),
    ExFishingStart(0xFE, 31),
    ExFishingEnd(0xFE, 32),
    ExShowQuestInfo(0xFE, 33),
    ExShowQuestMark(0xFE, 34),
    ExSendManorList(0xFE, 35),
    ExShowSeedInfo(0xFE, 36),
    ExShowCropInfo(0xFE, 37),
    ExShowManorDefaultInfo(0xFE, 38),
    ExShowSeedSetting(0xFE, 39),
    ExFishingStartCombat(0xFE, 40),
    ExFishingHpRegen(0xFE, 41),
    ExEnchantSkillList(0xFE, 42),
    ExEnchantSkillInfo(0xFE, 43),
    ExShowCropSetting(0xFE, 44),
    ExShowSellCropList(0xFE, 45),
    ExOlympiadMatchEnd(0xFE, 46),
    ExMailArrived(0xFE, 47),
    ExStorageMaxCount(0xFE, 48),
    ExEventMatchManage(0xFE, 49),
    ExMultiPartyCommandChannelInfo(0xFE, 50),
    ExPCCafePointInfo(0xFE, 51),
    ExSetCompassZoneCode(0xFE, 52),
    ExGetBossRecord(0xFE, 53),
    ExAskJoinPartyRoom(0xFE, 54),
    ExListPartyMatchingWaitingRoom(0xFE, 55),
    ExSetMpccRouting(0xFE, 56),
    ExShowAdventurerGuideBook(0xFE, 57),
    ExShowScreenMessage(0xFE, 58),
    PledgeSkillList(0xFE, 59),
    PledgeSkillListAdd(0xFE, 60),
    PledgeSkillListRemove(0xFE, 61),
    PledgePowerGradeList(0xFE, 62),
    PledgeReceivePowerInfo(0xFE, 63),
    PledgeReceiveMemberInfo(0xFE, 64),
    PledgeReceiveWarList(0xFE, 65),
    PledgeReceiveSubPledgeCreated(0xFE, 66),
    ExRedSky(0xFE, 67),
    PledgeReceiveUpdatePower(0xFE, 68),
    FlySelfDestination(0xFE, 69),
    ShowPCCafeCouponShowUI(0xFE, 70),
    ExSearchOrc(0xFE, 71),
    ExCursedWeaponList(0xFE, 72),
    ExCursedWeaponLocation(0xFE, 73),
    ExRestartClient(0xFE, 74),
    ExRequestHackShield(0xFE, 75),
    ExUseSharedGroupItem(0xFE, 76),
    ExMPCCShowPartyMemberInfo(0xFE, 77),
    ExDuelAskStart(0xFE, 78),
    ExDuelReady(0xFE, 79),
    ExDuelStart(0xFE, 80),
    ExDuelEnd(0xFE, 81),
    ExDuelUpdateUserInfo(0xFE, 82),
    ExShowVariationMakeWindow(0xFE, 83),
    ExShowVariationCancelWindow(0xFE, 84),
    ExPutItemResultForVariationMake(0xFE, 85),
    ExPutIntensiveResultForVariationMake(0xFE, 86),
    ExPutCommissionResultForVariationMake(0xFE, 87),
    ExVariationResult(0xFE, 88),
    ExPutItemResultForVariationCancel(0xFE, 89),
    ExVariationCancelResult(0xFE, 90),
    ExDuelEnemyRelation(0xFE, 91),
    ExPlayAnimation(0xFE, 92),
    ExMPCCPartyInfoUpdate(0xFE, 93),
    ExPlayScene(0xFE, 94),
    ExSpawnEmitter(0xFE, 0x5e), //у нас просто SpawnEmitter
    ExEnchantSkillInfoDetail(0xFE, 96),
    ExBasicActionList(0xFE, 97),
    ExAirShipInfo(0xFE, 98),
    ExAttributeEnchantResult(0xFE, 99),
    ExChooseInventoryAttributeItem(0xFE, 100),
    ExGetOnAirShip(0xFE, 101),
    ExGetOffAirShip(0xFE, 102),
    ExMoveToLocationAirShip(0xFE, 103),
    ExStopMoveAirShip(0xFE, 104),
    ExShowTrace(0xFE, 105),
    ExItemAuctionInfo(0xFE, 106),
    ExNeedToChangeName(0xFE, 107),
    ExPartyPetWindowDelete(0xFE, 108),
    ExTutorialList(0xFE, 109),
    ExRpItemLink(0xFE, 110),
    ExMoveToLocationInAirShip(0xFE, 111),
    ExStopMoveInAirShip(0xFE, 112),
    ExValidateLocationInAirShip(0xFE, 113),
    ExUISetting(0xFE, 114),
    ExMoveToTargetInAirShip(0xFE, 115),
    ExAttackInAirShip(0xFE, 116),
    ExMagicSkillUseInAirShip(0xFE, 117),
    ExShowBaseAttributeCancelWindow(0xFE, 118),
    ExBaseAttributeCancelResult(0xFE, 119),
    ExSubPledgetSkillAdd(0xFE, 120),
    ExResponseFreeServer(0xFE, 121),
    ExShowProcureCropDetail(0xFE, 122),
    ExHeroList(0xFE, 123),
    ExOlympiadUserInfo(0xFE, 124),
    ExOlympiadSpelledInfo(0xFE, 125),
    ExOlympiadMode(0xFE, 126),
    ExShowFortressMapInfo(0xFE, 127),
    ExPVPMatchRecord(0xFE, 128),
    ExPVPMatchUserDie(0xFE, 129),
    ExPrivateStoreWholeMsg(0xFE, 130),
    ExPutEnchantTargetItemResult(0xFE, 131),
    ExPutEnchantSupportItemResult(0xFE, 132),
    ExChangeNicknameNColor(0xFE, 133),
    ExGetBookMarkInfo(0xFE, 134),
    ExNotifyPremiumItem(0xFE, 135),
    ExGetPremiumItemList(0xFE, 136),
    ExPeriodicItemList(0xFE, 137),
    ExJumpToLocation(0xFE, 138),
    ExPVPMatchCCRecord(0xFE, 139),
    ExPVPMatchCCMyRecord(0xFE, 140),
    ExPVPMatchCCRetire(0xFE, 141),
    ExShowTerritory(0xFE, 142),
    ExNpcQuestHtmlMessage(0xFE, 143),
    ExSendUIEvent(0xFE, 144),
    ExNotifyBirthDay(0xFE, 145),
    ExShowDominionRegistry(0xFE, 146),
    ExReplyRegisterDominion(0xFE, 147),
    ExReplyDominionInfo(0xFE, 148),
    ExShowOwnthingPos(0xFE, 149),
    ExCleftList(0xFE, 150),
    ExCleftState(0xFE, 151),
    ExDominionChannelSet(0xFE, 152),
    ExBlockUpSetList(0xFE, 153),
    ExBlockUpSetState(0xFE, 154),
    ExStartScenePlayer(0xFE, 155),
    ExAirShipTeleportList(0xFE, 156),
    ExMpccRoomInfo(0xFE, 157),
    ExListMpccWaiting(0xFE, 158),
    ExDissmissMpccRoom(0xFE, 159),
    ExManageMpccRoomMember(0xFE, 160),
    ExMpccRoomMember(0xFE, 161),
    ExVitalityPointInfo(0xFE, 162),
    ExShowSeedMapInfo(0xFE, 163),
    ExMpccPartymasterList(0xFE, 164),
    ExDominionWarStart(0xFE, 165),
    ExDominionWarEnd(0xFE, 166),
    ExShowLines(0xFE, 167),
    ExPartyMemberRenamed(0xFE, 168),
    ExEnchantSkillResult(0xFE, 169),
    ExRefundList(0xFE, 170),
    ExNoticePostArrived(0xFE, 171),
    ExShowReceivedPostList(0xFE, 172),
    ExReplyReceivedPost(0xFE, 173),
    ExShowSentPostList(0xFE, 174),
    ExReplySentPost(0xFE, 175),
    ExResponseShowStepOne(0xFE, 176),
    ExResponseShowStepTwo(0xFE, 177),
    ExResponseShowContents(0xFE, 178),
    ExShowPetitionHtml(0xFE, 179),
    ExReplyPostItemList(0xFE, 180),
    ExChangePostState(0xFE, 181),
    ExReplyWritePost(0xFE, 182),
    ExInitializeSeed(0xFE, 183),
    ExRaidReserveResult(0xFE, 184),
    ExBuySellList(0xFE, 185),
    ExCloseRaidSocket(0xFE, 186),
    ExPrivateMarketList(0xFE, 187),
    ExRaidCharacterSelected(0xFE, 188),
    ExAskCoupleAction(0xFE, 189),
    ExBrBroadcastEventState(0xFE, 190),
    ExBR_LoadEventTopRankers(0xFE, 191),
    ExChangeNPCState(0xFE, 192),
    ExAskModifyPartyLooting(0xFE, 193),
    ExSetPartyLooting(0xFE, 194),
    ExRotation(0xFE, 195),
    ExChangeClientEffectInfo(0xFE, 196),
    ExMembershipInfo(0xFE, 197),
    ExReplyHandOverPartyMaster(0xFE, 198),
    ExQuestNpcLogList(0xFE, 199),
    ExQuestItemList(0xFE, 200),
    ExGMViewQuestItemList(0xFE, 201),
    ExResartResponse(0xFE, 202),
    ExVoteSystemInfo(0xFE, 203),
    ExShuttuleInfo(0xFE, 204),
    ExSuttleGetOn(0xFE, 205),
    ExSuttleGetOff(0xFE, 206),
    ExSuttleMove(0xFE, 207),
    ExMTLInSuttle(0xFE, 208),
    ExStopMoveInShuttle(0xFE, 209),
    ExValidateLocationInShuttle(0xFE, 210),
    ExAgitAuctionCmd(0xFE, 211),
    ExConfirmAddingPostFriend(0xFE, 212),
    ExReceiveShowPostFriend(0xFE, 213),
    ExReceiveOlympiad(0xFE, 214),
    ExBR_GamePoint(0xFE, 215),
    ExBR_ProductList(0xFE, 216),
    ExBR_ProductInfo(0xFE, 217),
    ExBR_BuyProduct(0xFE, 218),
    ExBR_PremiumState(0xFE, 219),
    ExBrExtraUserInfo(0xFE, 220),
    ExBrBuffEventState(0xFE, 221),
    ExBR_RecentProductList(0xFE, 222),
    ExBR_MinigameLoadScores(0xFE, 223),
    ExBR_AgathionEnergyInfo(0xFE, 224),
    ExShowChannelingEffect(0xFE, 225),
    ExGetCrystalizingEstimation(0xFE, 226),
    ExGetCrystalizingFail(0xFE, 227),
    ExNavitAdventPointInfo(0xFE, 228),
    ExNavitAdventEffect(0xFE, 229),
    ExNavitAdventTimeChange(0xFE, 230),
    ExAbnormalStatusUpdateFromTarget(0xFE, 231),
    ExStopScenePlayer(0xFE, 232),
    ExFlyMove(0xFE, 233),
    ExDynamicQuest(0xFE, 234),
    ExSubjobInfo(0xFE, 235),
    ExChangeMPCost(0xFE, 236),
    ExFriendDetailInfo(0xFE, 237),
    ExBlockAddResult(0xFE, 238),
    ExBlockRemoveResult(0xFE, 239),
    ExBlockDefailInfo(0xFE, 240),
    ExLoadInzonePartyHistory(0xFE, 241),
    ExFriendNotifyNameChange(0xFE, 242),
    ExShowCommission(0xFE, 243),
    ExResponseCommissionItemList(0xFE, 244),
    ExResponseCommissionInfo(0xFE, 245),
    ExResponseCommissionRegister(0xFE, 246),
    ExResponseCommissionDelete(0xFE, 247),
    ExResponseCommissionList(0xFE, 248),
    ExResponseCommissionBuyInfo(0xFE, 249),
    ExResponseCommissionBuyItem(0xFE, 250),
    ExAcquirableSkillListByClass(0xFE, 251),
    ExMagicAttackInfo(0xFE, 252),
    ExAcquireSkillInfo(0xFE, 253),
    ExNewSkillToLearnByLevelUp(0xFE, 0xFE),
    ExCallToChangeClass(0xFE, 255),
    ExChangeToAwakenedClass(0xFE, 256),
    ExTacticalSign(0xFE, 257),
    ExLoadStatWorldRank(0xFE, 258),
    ExLoadStatUser(0xFE, 259),
    ExLoadStatHotLink(0xFE, 260),
    ExWaitWaitingSubStituteInfo(0xFE, 261),
    ExRegistWaitingSubstituteOk(0xFE, 262),
    ExRegistPartySubstitute(0xFE, 263),
    ExDeletePartySubstitute(0xFE, 264),
    ExTimeOverPartySubstitute(0xFE, 265),
    ExGet24HzSessionID(0xFE, 266),
    Ex2NDPasswordCheck(0xFE, 267),
    Ex2NDPasswordVerify(0xFE, 268),
    Ex2NDPasswordAck(0xFE, 269),
    ExFlyMoveBroadcast(0xFE, 270),
    ExShowUsm(0xFE, 271),
    ExShowStatPage(0xFE, 272),
    ExIsCharNameCreatable(0xFE, 273),
    ExGoodsInventoryChangedNotify(0xFE, 274),
    ExGoodsInventoryInfo(0xFE, 275),
    ExGoodsInventoryResult(0xFE, 276),
    ExAlterSkillRequest(0xFE, 277),
    ExNotifyFlyMoveStart(0xFE, 278),
    ExDummy(0xFE, 279),
    ExCloseCommission(0xFE, 280),
    ExChangeAttributeItemList(0xFE, 281),
    ExChangeAttributeInfo(0xFE, 282),
    ExChangeAttributeOk(0xFE, 283),
    ExChangeAttributeFail(0xFE, 284),
    ExExchangeSubstitute(0xFE, 285),
    ExLightingCandleEvent(0xFE, 286),
    ExVitalityEffectInfo(0xFE, 287),
    ExLoginVitalityEffectInfo(0xFE, 288),
    ExBR_PresentBuyProduct(0xFE, 289),
    ExMentorList(0xFE, 290),
    ExMentorAdd(0xFE, 291),
    ListMenteeWaiting(0xFE, 292),
    ExInzoneWaitingInfo(0xFE, 293),
    ExCuriousHouseState(0xFE, 294),
    ExCuriousHouseEnter(0xFE, 295),
    ExCuriousHouseLeave(0xFE, 296),
    ExCuriousHouseMemberList(0xFE, 297),
    ExCuriousHouseMemberUpdate(0xFE, 298),
    ExCuriousHouseRemainTime(0xFE, 299),
    ExCuriousHouseResult(0xFE, 300),
    ExCuriousHouseObserveList(0xFE, 301),
    ExCuriousHouseObserveMode(0xFE, 302),
    ExSysstring(0xFE, 303),
    ExChoose_Shape_Shifting_Item(0xFE, 304),
    ExPut_Shape_Shifting_Target_Item_Result(0xFE, 305),
    ExPut_Shape_Shifting_Extraction_Item_Result(0xFE, 306),
    ExShape_Shifting_Result(0xFE, 307),
    ExCastleState(0xFE, 308),
    ExNCGuardReceiveDataFromServer(0xFE, 309),
    ExKalieEvent(0xFE, 310),
    ExPledgeUnionState(0xFE, 311),
    ExPledgeUnionFlow(0xFE, 312),
    ExPledgeUnionStateInfo(0xFE, 313),
    ExUnionPoint(0xFE, 314),
    ExKalieEventJackpotUser(0xFE, 315),
    ExAbnormalVisualEffectInfo(0xFE, 316),
    ExNpcSpeedInfo(0xFE, 317),
    ExCheck_SpeedHack(0xFE, 318),
    ExBR_NewIConCashBtnWnd(0xFE, 319),
    ExEvent_Campaign_Info(0xFE, 320),
    ExValidateActiveCharacter(0xFE, 321),
    ExCloseCommissionRegister(0xFE, 322),
    ExTeleportToLocationActivate(0xFE, 0x142);

    private int id;
    private int ex;

    private ServerPacket(int _id, int _ex) {
        id = _id;
        ex = _ex;
    }

    public int getId() {
        return id;
    }

    public int getEx() {
        return ex;
    }
}
