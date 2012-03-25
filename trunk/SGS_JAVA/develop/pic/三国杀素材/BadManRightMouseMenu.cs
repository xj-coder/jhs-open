
function AddToFriendGroup()
{
   %nick= mBadmanRightMouseMenu.GetNickname();
   mBadmanRightMouseMenu.setVisible(0);
   SendMoveToFriendGroupMsg(%nick);
}

function DeleteFriendInBadMan()
{
   %nick= mBadmanRightMouseMenu.GetNickname();
   //SendDeleteFriendMsg(%nick);
   mFriendRightMouseMenu.setVisible(0);
   mBadmanRightMouseMenu.setVisible(0);
   pushDelBadManFriendConfirmDlg(%nick);
}

