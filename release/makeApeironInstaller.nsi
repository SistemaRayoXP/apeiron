!include "MUI.nsh"

!insertmacro MUI_PAGE_COMPONENTS 
!insertmacro MUI_PAGE_DIRECTORY 
!insertmacro MUI_PAGE_INSTFILES 
!define MUI_FINISHPAGE_RUN "$INSTDIR\apeiron.exe" 
!define MUI_FINISHPAGE_RUN_TEXT "Iniciar Apeiron"
!insertmacro MUI_PAGE_FINISH

!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
!insertmacro MUI_UNPAGE_FINISH

!insertmacro MUI_LANGUAGE "Spanish"

name "Apeiron"
caption "Instalar Apeiron"
outFile "installer\instalarApeiron.exe"
InstallDir "$PROGRAMFILES\Apeiron"

section "Apeiron"
    setOutPath $INSTDIR
    file /r 'bin\*.jar'
    file /r 'bin\*.dll'
    file /r 'bin\*.bat'
    
    file loader\apeiron.exe
    createShortCut "$DESKTOP\Apeiron.lnk" "$INSTDIR\apeiron.exe"
    
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Apeiron" \
                 "DisplayName" "Apeiron"
                 
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Apeiron" \
                 "UninstallString" "$INSTDIR\uninstaller.exe"
                 
    writeUninstaller $INSTDIR\uninstaller.exe
sectionEnd
 
section "Uninstall" "Apeiron"
    delete "$DESKTOP\Apeiron.lnk"
    RMDir  /r "$INSTDIR"
    DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\Apeiron"
sectionEnd