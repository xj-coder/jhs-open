function OnLaBaDlgMsgChange()
{
	return;
	echo("aaaaaaaaaaaaaaa");
	%charNum = LaBaDlg_InputText.getNumChars();
	echo("�ַ�����" @ %charNum);
	%msgText = "������" @ %charNum @ "/60";
	LaBaDlg_MaxTextMsg.setText(%msgText);
}
