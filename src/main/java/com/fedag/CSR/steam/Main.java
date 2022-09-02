package com.fedag.CSR.steam;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.lukaspradel.steamapi.data.json.appnews.GetNewsForApp;
import com.lukaspradel.steamapi.data.json.appnews.Newsitem;
import com.lukaspradel.steamapi.data.json.friendslist.Friend;
import com.lukaspradel.steamapi.data.json.friendslist.Friendslist;
import com.lukaspradel.steamapi.data.json.friendslist.GetFriendList;
import com.lukaspradel.steamapi.data.json.ownedgames.Game;
import com.lukaspradel.steamapi.data.json.ownedgames.GetOwnedGames;
import com.lukaspradel.steamapi.webapi.client.SteamWebApiClient;
import com.lukaspradel.steamapi.webapi.request.GetFriendListRequest;
import com.lukaspradel.steamapi.webapi.request.GetNewsForAppRequest;
import com.lukaspradel.steamapi.webapi.request.GetOwnedGamesRequest;
import com.lukaspradel.steamapi.webapi.request.builders.SteamWebApiRequestFactory;

import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws SteamApiException {

//        SteamWebApiClient client = new SteamWebApiClient.SteamWebApiClientBuilder("F763327BB1CA8AFC6BEB4CC99BE9FB33").build();
//        GetFriendListRequest request = SteamWebApiRequestFactory.createGetFriendListRequest("76561197994368491");

//        GetOwnedGames getNewsForApp = client.processRequest(request);
////        List<String> s = getNewsForApp.getAppnews().getNewsitems().stream().map(Newsitem::getContents).collect(Collectors.toList());
//        List<Integer> s = getNewsForApp.getResponse().getGames().stream().map(Game::getAppid).collect(Collectors.toList());
//        GetFriendList friendslist = client.processRequest(request);
//
//
//        List<Friend> s = friendslist.getFriendslist().getFriends();
//        int i = 0;
//        for (Friend f: s) {
//            System.out.println(f);
//            if (f.getSteamid().equals("76561199207441835"))
//                System.out.println(i);
//            i++;
//        }

//        System.out.println("=-----------------------------------------");
//        System.out.println(s);
    }
}
