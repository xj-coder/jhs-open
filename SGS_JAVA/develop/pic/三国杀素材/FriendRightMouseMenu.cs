function ShowFriendUserInfo()
{
   %nick= mFriendRightMouseMenu.GetNickname();
   SendGetUserInfoMsg(%nick);
   mFriendRightMouseMenu.setVisible(0);
   mBadmanRightMouseMenu.setVisible(0);
}

function DeleteFriend()
{
   %nick= mFriendRightMouseMenu.GetNickname();
   //SendDeleteFriendMsg(%nick);
   mFriendRightMouseMenu.setVisible(0);
   mBadmanRightMouseMenu.setVisible(0);
   pushDelFriendConfirmDlg(%nick);
}

function ShowFriendChatDlg()
{
   %nick= mFriendRightMouseMenu.GetNickname();
   mFriendRightMouseMenu.setVisible(0);
   
   mRoomChatPlayerList.removebyId(3);
   mRoomChatPlayerList.add(%nick, 3);
   mRoomChatPlayerList.setText(%nick);  
   
   //…Ë÷√π‚±Í
   mRoomChatInput.setCursorPos(strlen(mRoomChatInput.getText()));
   mRoomChatInput.makeFirstResponder(true);
}
function AddToBadManGroup()
{
   %nick= mFriendRightMouseMenu.GetNickname();
   mFriendRightMouseMenu.setVisible(0);
   SendMoveToBadmanGroupMsg(%nick);
}
function MoveFriendToGroupName()
{
   PushGroupNameMenu();
}

mSelroomGui.add(mFriendGroupNameMenu);