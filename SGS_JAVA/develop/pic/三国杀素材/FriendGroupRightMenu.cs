function AddFriendGroup()
{
   
   PushDialog(mAddFriendGroup);
   mFriendGroupRightMenu.setVisible(0);
   mFriendGroupRightMenu2.setVisible(0);
}
function ReNameFriendGroup()
{
   %nick= mFriendGroupRightMenu.GetNickname();
   PushDialog(mReNameFriendGroup);
   mReNameFriendGroupTextCtl.setText(%nick);
   mFriendGroupRightMenu.setVisible(0);  
   mFriendGroupRightMenu2.setVisible(0); 
}
function DeleteFriendGroup()
{
   %nick= mFriendGroupRightMenu.GetNickname();
   SendDeleteFriendGroupMsg(%nick);
   mFriendGroupRightMenu.setVisible(0);
   mFriendGroupRightMenu2.setVisible(0);
}


function AddFriendGroupFun()
{
   SendAddFriendGroupMsg(mAddFriendGroupEditCtl.getText());
   PopDialog(mAddFriendGroup);
}
function ReNameFriendGroupFun()
{
   %nick= mFriendGroupRightMenu.GetNickname();
   SendReNameFriendGroupMsg(%nick,mReNameFriendGroupEditCtl.getText());
  
   PopDialog(mReNameFriendGroup);
}
