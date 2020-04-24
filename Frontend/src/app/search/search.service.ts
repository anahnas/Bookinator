import { Injectable } from '@angular/core';

@Injectable()
export class SearchService{
    getGenres() : any[]{
        return[
            {"name":"Realizam"},
            {"name":"Fantasika"},
            {"name":"Kriminalisika"},
            {"name":"Romantizam"},
            {"name":"Horor"},
            {"name":"Drama"},
            {"name":"Komedija"},
            {"name":"Enciklopedistika"},
            {"name":"Istorijski romani"},
            {"name":"Ratni romani"},
            {"name":"Novele"}
        ]
        
    };
    getStyles() : any[]{
        return[
            {"name":"Antika"},
            {"name":"Srednjovekovna književnost"},
            {"name":"Renesansa"},
            {"name":"Barok"},
            {"name":"Romantizam"},
            {"name":"Moderna"},
            {"name":"Savremena književnost"},
            {"name":"Naučna literatura"}
        ]
        
    }
}