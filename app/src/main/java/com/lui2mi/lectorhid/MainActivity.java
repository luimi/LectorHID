package com.lui2mi.lectorhid;

import android.smartcardio.Card;
import android.smartcardio.CardChannel;
import android.smartcardio.CardTerminal;
import android.smartcardio.CommandAPDU;
import android.smartcardio.ResponseAPDU;
import android.smartcardio.TerminalFactory;
import android.smartcardio.ipc.CardService;
import android.smartcardio.ipc.ICardService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ICardService mService;
    private TerminalFactory mFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = CardService.getInstance(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mService.releaseService();
    }

    public void read(View v) {
        try{
            mFactory = mService.getTerminalFactory();
            List<CardTerminal> terminals = mFactory.terminals().list();
            // Get the first terminal and establish a card connection.
            CardTerminal terminal_1 = terminals.get(0);
            Card card = terminal_1.connect("T=0");
            CardChannel channel = card.getBasicChannel();
            // Send a command to the card and receive the response.
            byte[] command = { 0x00, 0x04, 0x00, 0x0C, 0x02, 0x3F, 0x00 };
            ResponseAPDU response = channel.transmit(new CommandAPDU(command));
            // Interpret response, do further work.
            // ...
            // At the end, release card connection.
            card.disconnect(true);
        }catch (Exception ignored){}
    }
}
