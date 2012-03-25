function OnLaBaDlgMsgChange()
{
	return;
	echo("aaaaaaaaaaaaaaa");
	%charNum = LaBaDlg_InputText.getNumChars();
	echo("×Ö·ûÊı£º" @ %charNum);
	%msgText = "¿ÉÊäÈë" @ %charNum @ "/60";
	LaBaDlg_MaxTextMsg.setText(%msgText);
}
