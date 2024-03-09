package com.project.youtubeplaylistrecommend.board;

import com.project.youtubeplaylistrecommend.board.model.BoardPlaylistInsDto;
import com.project.youtubeplaylistrecommend.board.model.BoardPlaylistSelVo;
import com.project.youtubeplaylistrecommend.common.BoardEnum;
import com.project.youtubeplaylistrecommend.genre.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final GenreService genreService;

    @GetMapping
    public String getBoard(@RequestParam(name = "code") int code, Pageable pageable, Model model) {
        model.addAttribute("code", code);
        model.addAttribute("title", getBoardName(code));
        if (code == BoardEnum.MUSIC_RECOMMEND.getCode()) {
            model.addAttribute("playlist", boardService.getPlaylistBoard(pageable));
            return "/board/list-playlist";
        }
        return "/board/list";
    }

    @GetMapping("/read-playlist/{iboard}")
    public String selPlaylistBoard(@RequestParam(name = "code") int code, @PathVariable long iboard, Model model) {
        model.addAttribute("code", code);
        model.addAttribute("title", getBoardName(code));
        model.addAttribute("board", boardService.selPlaylistBoard(iboard));
        log.info("board = {}", boardService.selPlaylistBoard(iboard));
        return "/board/read-playlist";
    }

    @PostMapping("/write-playlist")
    @ResponseBody
    public long insPlaylistBoard(@RequestBody BoardPlaylistInsDto dto) {
        return boardService.insPlaylistBoard(dto);
    }

    @GetMapping("/write")
    public String insBoard(@RequestParam(name = "code") int code, Model model) {
        if (code == BoardEnum.MUSIC_RECOMMEND.getCode()) {
            model.addAttribute("code", code);
            model.addAttribute("genre", genreService.getGenre());
            return "/board/write-playlist";
        }
        return "/home";
    }

    private String getBoardName(int code) {
        if (code == 0) {
            return null;
        } else {
            String codeToString = null;

            switch (code) {
                case 1 -> codeToString = BoardEnum.NOTICE.getName();
                case 2 -> codeToString = BoardEnum.MUSIC_RECOMMEND.getName();
                case 3 -> codeToString = BoardEnum.FREE.getName();
            }
            return codeToString;
        }
    }
}
